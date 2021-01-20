package webservice

import grails.gorm.transactions.Transactional

@Transactional
class EntryStockPlateService {

    static List<EntryStockPlate> getEntryStockPlates() {
        return EntryStockPlate.list()
    }

    static registerEntryStockPlate(EntryStockPlate entryStockPlate) {
        String type = entryStockPlate.type
        Integer quantity = entryStockPlate.quantity

        def newEntryStockPlate = new EntryStockPlate(type: type, quantity: quantity)
        newEntryStockPlate.save(failOnError: true, flush: true)

        return newEntryStockPlate
    }

}
