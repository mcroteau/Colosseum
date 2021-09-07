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
        String password = Chico.dirty(Sigma.SUPER_PASSWORD);

        Role superRole = roleRepo.find(Sigma.SUPER_ROLE);
        Role scholarRole = roleRepo.find(Sigma.SCHOLAR_ROLE);
        Role professorRole = roleRepo.find(Sigma.PROFESSOR_ROLE);

        if(superRole == null){
            superRole = new Role();
            superRole.setName(Sigma.SUPER_ROLE);
            roleRepo.save(superRole);
        }

        if(scholarRole == null){
            scholarRole = new Role();
            scholarRole.setName(Sigma.SCHOLAR_ROLE);
            roleRepo.save(scholarRole);
        }

        if(professorRole == null){
            professorRole = new Role();
            professorRole.setName(Sigma.PROFESSOR_ROLE);
            roleRepo.save(professorRole);
        }

        User existingSuper = userRepo.get(Sigma.SUPER);
        if(existingSuper == null){
            User superUser = new User();
            superUser.setUsername(Sigma.SUPER);
            superUser.setPassword(password);
            userRepo.saveAdministrator(superUser);
        }

        User existingScholar = userRepo.get(Sigma.SCHOLAR);
        if(existingScholar == null){
            User scholar = new User();
            scholar.setUsername(Sigma.SCHOLAR);
            scholar.setPassword(password);
            userRepo.saveAdministrator(scholar);
        }

        User existingProfessor = userRepo.get(Sigma.PROFESSOR);
        if(existingProfessor == null){
            User professor = new User();
            professor.setUsername(Sigma.PROFESSOR);
            professor.setPassword(password);
            userRepo.saveAdministrator(professor);
        }


//        try {
//
//            Class[] jobs = { HealthJob.class, HealthJobDos.class, HealthJobTres.class };
//            String[] jobNames = { Sigma.HEALTH_JOB1, Sigma.HEALTH_JOB2, Sigma.HEALTH_JOB3 };
//            String[] triggers = { Sigma.HEALTH1_TRIGGER, Sigma.HEALTH2_TRIGGER, Sigma.HEALTH3_TRIGGER };
//
//
//            for(int n = 0; n < jobs.length; n++){
//
//                JobDetail job = JobBuilder.newJob(jobs[n])
//                        .withIdentity(jobNames[n], Sigma.HEALTH_GROUP).build();
//
//                job.getJobDataMap().put(Sigma.PROJECT_REPO_KEY, videoRepo);
//                job.getJobDataMap().put(Sigma.PHONE_SERVICE_KEY, smsService);
//
//                Trigger trigger = TriggerBuilder
//                        .newTrigger()
//                        .withIdentity(triggers[n], Sigma.HEALTH_GROUP)
//                        .withSchedule(
//                                SimpleScheduleBuilder.simpleSchedule()
//                                        .withIntervalInSeconds(Sigma.HEALTH_JOBS_DURATION).repeatForever())
//                        .build();
//
//                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//                scheduler.startDelayed(0 );
//                JobKey key = new JobKey(jobNames[n], Sigma.HEALTH_GROUP);
//                if(!scheduler.checkExists(key)) {
//                    scheduler.scheduleJob(job, trigger);
//                    System.out.println(jobs[n] + " repeated " + Sigma.HEALTH_JOBS_DURATION + " seconds");
//                }
//            }
//
//        }catch(Exception e){
//            System.out.println("issue initializing job" + e.getMessage());
//        }

    }

}
