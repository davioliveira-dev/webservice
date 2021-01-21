package webservice

import grails.artefact.controller.RestResponder
import grails.rest.*
import org.springframework.security.access.annotation.Secured

@Secured('isAuthenticated()')
class ValidateEntryStockPlateController extends RestfulController implements RestResponder {
	static responseFormats = ['json', 'xml']

    ValidateEntryStockPlateController() {
        super(ValidateEntryStockPlateController)
    }

    EntryStockPlateService entryStockPlateService = new EntryStockPlateService()

    def index() {
        respond entryStockPlateService.getEntryStockPlates()
    }

    def create(EntryStockPlate entryStockPlate) {
        String type = entryStockPlate.type
        Integer quantity = entryStockPlate.quantity

        if (!type || !quantity) {
            return response.sendError(400, 'ERRO: Diga o tipo e a quantidade de placas!')
        }

        try {
            def newEntryStockPlate = entryStockPlateService.registerEntryStockPlate(entryStockPlate)
            response.setStatus(201)
            respond newEntryStockPlate
        }
        catch (e) {
            println(e.message)
            return respond (e.message)
        }
    }
}
