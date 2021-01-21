package webservice

import org.grails.datastore.gorm.GormEntity

class User implements GormEntity<User> {
    String username

    static constraints = {
    }
}
