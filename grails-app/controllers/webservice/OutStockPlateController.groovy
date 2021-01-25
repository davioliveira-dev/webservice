package webservice

import grails.artefact.controller.RestResponder
import grails.rest.*
import org.springframework.security.access.annotation.Secured

@Secured(["IS_AUTHENTICATED_ANONYMOUSLY"])
class OutStockPlateController extends RestfulController implements RestResponder {
	static responseFormats = ['json', 'xml']

    OutStockPlateController() {
        super(OutStockPlateController)
    }

    OutStockPlateService outStockPlateService = new OutStockPlateService()

    def index() {
        AuthMiddleware.hasValidToken(request, response)

        respond outStockPlateService.getOutStockPlates()
    }

    def create(OutStockPlate outStockPlate) {
        AuthMiddleware.hasValidToken(request, response)

        String type = outStockPlate.type
        Integer quantity = outStockPlate.quantity
        String numbering = outStockPlate.numbering

        if (!type || !quantity || !numbering) {
            return response.sendError(400, 'ERRO: Diga o tipo, quantidade e a numeração da(s) placa(s)!')
        }

        try {
            def newOutStockPlate = outStockPlateService.registerOutStockPlate(outStockPlate)
            response.setStatus(201)
            respond newOutStockPlate
        }
        catch (e) {
            println(e.message)
            return response.sendError(500, 'ERRO: Erro interno do servidor')
        }
    }

}
