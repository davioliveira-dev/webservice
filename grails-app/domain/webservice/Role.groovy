package webservice

import org.grails.datastore.gorm.GormEntity

class Role implements GormEntity<Role> {
    String authority

    String toString() {
        authority
    }

    static constraints = {
    }
}
