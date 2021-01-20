package webservice

class UrlMappings {

    static mappings = {
        get "/listaRegistroEntradaPlaca"(controller: "ValidateEntryStockPlate", action: "index")
        post "/registraEntradaPlaca"(controller: "ValidateEntryStockPlate", action: "create")
        get "/listaRegistroSaidaPlaca"(controller: "OutStockPlate", action: "index")
        post "/registraSaidaPlaca"(controller: "OutStockPlate", action: "create")

        "403"(controller: "errors", action: "forbidden")
        "404"(controller: "errors", action: "notFound")
        "500"(controller: "errors", action: "serverError")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
