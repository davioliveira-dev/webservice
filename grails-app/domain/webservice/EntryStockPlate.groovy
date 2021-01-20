package webservice

import org.grails.datastore.gorm.GormEntity

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EntryStockPlate implements GormEntity<EntryStockPlate> {

    String id
    String entryDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    String type
    Integer quantity

    static constraints = {
        id column: 'id', generator: 'uuid'
        entryDate blank: true, nullable: true
        type size: 1..30, blank: false, nullable: false
        quantity blank: false, nullable: false
    }
}
