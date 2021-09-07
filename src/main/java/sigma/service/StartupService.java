package sigma.service;

import sigma.Sigma;
import sigma.model.Video;
import sigma.repo.VideoRepo;
import chico.Chico;
import sigma.support.Access;
import sigma.model.Role;
import sigma.model.User;
import sigma.repo.RoleRepo;
import sigma.repo.UserRepo;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import qio.annotate.Inject;
import qio.annotate.Service;

@Service
public class StartupService {

    @Inject
    UserRepo userRepo;

    @Inject
    RoleRepo roleRepo;

    @Inject
    VideoRepo videoRepo;

    @Inject
    Access access;

    @Inject
    SmsService smsService;


    public void init() throws Exception {

        Chico.configure(access);

        Role superRole = roleRepo.find(Sigma.SUPER_ROLE);
        Role userRole = roleRepo.find(Sigma.USER_ROLE);

        if(superRole == null){
            superRole = new Role();
            superRole.setName(Sigma.SUPER_ROLE);
            roleRepo.save(superRole);
        }

        if(userRole == null){
            userRole = new Role();
            userRole.setName(Sigma.USER_ROLE);
            roleRepo.save(userRole);
        }

        User existing = userRepo.getByUsername(Sigma.SUPER_USERNAME);
        String password = Chico.dirty(Sigma.SUPER_PASSWORD);

        if(existing == null){
            User superUser = new User();
            superUser.setUsername(Sigma.SUPER_USERNAME);
            superUser.setPassword(password);
            userRepo.saveAdministrator(superUser);
        }


        String[] uris = {"http://goioc.xyz",
                "http://opengreenfield.org"};

        User user = userRepo.getByUsername(Sigma.SUPER_USERNAME);
        for(int n = 0; n < uris.length; n++){
            Video video = new Video();
            video.setName(Sigma.getString(4) + " " + Sigma.getString(6));
            video.setUri(uris[n]);
            video.setUserId(user.getId());
            Video savedVideo = videoRepo.save(video);

            ProjectPhone projectPhone = new ProjectPhone();
            projectPhone.setPhone("9076879557");
            projectPhone.setProjectId(savedVideo.getId());
            videoRepo.addPhone(projectPhone);
        }


        try {

            Class[] jobs = { HealthJob.class, HealthJobDos.class, HealthJobTres.class };
            String[] jobNames = { Sigma.HEALTH_JOB1, Sigma.HEALTH_JOB2, Sigma.HEALTH_JOB3 };
            String[] triggers = { Sigma.HEALTH1_TRIGGER, Sigma.HEALTH2_TRIGGER, Sigma.HEALTH3_TRIGGER };


            for(int n = 0; n < jobs.length; n++){

                JobDetail job = JobBuilder.newJob(jobs[n])
                        .withIdentity(jobNames[n], Sigma.HEALTH_GROUP).build();

                job.getJobDataMap().put(Sigma.PROJECT_REPO_KEY, videoRepo);
                job.getJobDataMap().put(Sigma.PHONE_SERVICE_KEY, smsService);

                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(triggers[n], Sigma.HEALTH_GROUP)
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
                                        .withIntervalInSeconds(Sigma.HEALTH_JOBS_DURATION).repeatForever())
                        .build();

                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.startDelayed(0 );
                JobKey key = new JobKey(jobNames[n], Sigma.HEALTH_GROUP);
                if(!scheduler.checkExists(key)) {
                    scheduler.scheduleJob(job, trigger);
                    System.out.println(jobs[n] + " repeated " + Sigma.HEALTH_JOBS_DURATION + " seconds");
                }
            }

        }catch(Exception e){
            System.out.println("issue initializing job" + e.getMessage());
        }

    }

}
