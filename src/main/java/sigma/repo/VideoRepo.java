package sigma.repo;

import sigma.model.*;
import qio.Qio;
import qio.annotate.DataStore;
import qio.annotate.Inject;

import java.util.ArrayList;
import java.util.List;

@DataStore
public class VideoRepo {

    @Inject
    Qio qio;

    public Video getSaved() {
        String sql = "select * from video_sessions order by id desc limit 1";
        Video video = (Video) qio.get(sql, new Object[]{}, Video.class);
        return video;
    }

    public long getCount() {
        String sql = "select count(*) from video_sessions";
        Long count = (Long) qio.get(sql, new Object[] { }, Long.class);
        return count;
    }

    public Video get(long id){
        String sql = "select * from video_sessions where id = [+]";
        Video video = (Video) qio.get(sql, new Object[] { id }, Video.class);
        return video;
    }

    public List<Video> getList(){
        String sql = "select * from video_sessions";
        List<Video> videos = (ArrayList) qio.getList(sql, new Object[]{}, Video.class);
        return videos;
    }

    public List<Video> getOverview(long userId){
        String sql = "select * from video_sessions where user_id = [+] order by name asc";
        List<Video> videos = (ArrayList) qio.getList(sql, new Object[]{ userId }, Video.class);
        return videos;
    }

    public Boolean save(Video video){
        String sql = "insert into video_sessions (key, name, start_time) values ('[+]', '[+]', '[+]', [+])";
        qio.save(sql, new Object[] {
                video.getKey(),
                video.getName(),
                video.getStartTime()
        });
        return true;
    }


    public boolean update(Video video){
        String sql = "update video_sessions set name = '[+]', end_time = [+], status = '[+]' where id = [+]";
        qio.update(sql, new Object[] {
                video.getName(),
                video.getEndTime(),
                video.getStatus(),
                video.getId()
        });
        return true;
    }

    public boolean delete(long id){
        String sql = "delete from video_sessions where id = [+]";
        qio.delete(sql, new Object[] { id });
        return true;
    }

}
