package webservice

import grails.gorm.transactions.Transactional

@Transactional
class OutStockPlateService {

    static List<OutStockPlate> getOutStockPlates() {
        return OutStockPlate.list()
    }

    static OutStockPlate registerOutStockPlate(OutStockPlate outStockPlate) {
        def type = outStockPlate.type
        def quantity = outStockPlate.quantity
        def numbering = outStockPlate.numbering
        def cpfCnpj = outStockPlate.cpfCnpj
        def storeCnpj = outStockPlate.storeCnpj

        if (!cpfCnpj) {
            cpfCnpj = "null"
        }

        if(!storeCnpj) {
            storeCnpj = "null"
        }

        OutStockPlate newOutStockPlate = new OutStockPlate(
                type: type, quantity: quantity, numbering: numbering, cpfCnpj: cpfCnpj, storeCnpj: storeCnpj
        )

        newOutStockPlate.save(failOnError: true, flush: true)
        return newOutStockPlate
    }
}
