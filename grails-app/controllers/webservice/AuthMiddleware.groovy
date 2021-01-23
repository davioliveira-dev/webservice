package webservice

import groovy.json.JsonSlurper

class AuthMiddleware {
    static def hasValidToken(request, response) {
        String token = request.getHeader("Authorization")

        if (!token || token == "Bearer undefined" || token.length() <= 6) {
            return response.sendError(401, "Give me a token ;)")
        }

        def tokenParts = token.replaceAll("Bearer ", "").split("\\.")
        try {
            def bytes = Base64.getDecoder().decode(tokenParts[1])
            def tokenPayload = new String(bytes, "UTF-8")
            def tokenInJson = new JsonSlurper().parseText(tokenPayload)

            if (!tokenInJson.iss || !tokenInJson.exp || !tokenInJson.sub || !tokenInJson.aud || !tokenInJson.email) {
                return response.sendError(401, "Invalid Token!")
            }

        } catch(e) {
            println(e.message)
            return response.sendError(401, "Invalid Token!")
        }

        return true
    }
}
