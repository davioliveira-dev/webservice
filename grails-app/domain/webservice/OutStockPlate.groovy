package webservice

import org.grails.datastore.gorm.GormEntity

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OutStockPlate implements GormEntity<OutStockPlate> {

    String id
    String outDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    String type
    Integer quantity
    String numbering
    String cpfCnpj
    String storeCnpj

    static constraints = {
        id column: 'id', generator: 'uuid'
        outDate blank: false
        type size: 1..30, blank: false, nullable: false
        quantity blank: false, nullable: false
        numbering size: 1..9, blank: false, nullabe: false
        cpfCnpj size: 1..15, blank: true, nullabe: true
        storeCnpj size: 1..15, blank: true, nullabe: true
    }
}
