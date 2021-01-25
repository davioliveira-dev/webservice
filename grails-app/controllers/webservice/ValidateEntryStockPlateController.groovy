package webservice

import grails.artefact.controller.RestResponder
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

@Secured(["IS_AUTHENTICATED_ANONYMOUSLY"])
class ValidateEntryStockPlateController extends RestfulController implements RestResponder {
	static responseFormats = ['json', 'xml']

    ValidateEntryStockPlateController() {
        super(ValidateEntryStockPlateController)
    }

    EntryStockPlateService entryStockPlateService = new EntryStockPlateService()

    def index() {
        AuthMiddleware.hasValidToken(request, response)

        respond entryStockPlateService.getEntryStockPlates()
    }

    def create(EntryStockPlate entryStockPlate) {
        AuthMiddleware.hasValidToken(request, response)

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
