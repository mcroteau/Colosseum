package sigma.config;

import sigma.support.Papi;
import qio.annotate.Config;
import qio.annotate.Dependency;
import qio.annotate.Property;

import javax.sql.DataSource;

@Config
public class DbConfig {

    @Property("db.url")
    String url;

    @Property("db.user")
    String user;

    @Property("db.pass")
    String pass;

    @Property("db.driver")
    String driver;

    @Dependency
    public DataSource dataSource(){
        return new Papi.New()
                .connections(20)
                .driver(driver)
                .url(url)
                .user(user)
                .password(pass)
                .make();
    }

}
