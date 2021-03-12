package org.unicef.backlog.matchmaker

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kie.api.KieServices
import org.unicef.backlog.matchmaker.engine.EngineInstance
import org.unicef.backlog.matchmaker.repository.FirebaseRepo
import java.text.DateFormat


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

   val firebaseRepo = FirebaseRepo()

    routing {
        post("/matchmake") {

            val kServices = KieServices.Factory.get()
            val kContainer = kServices.kieClasspathContainer

            val dataInput = firebaseRepo.dataInput()
            val engineInstance = EngineInstance(kContainer, dataInput)
            val matchedSkills = engineInstance.matchedSkills()
            engineInstance.dispose()
            firebaseRepo.saveMatches(matchedSkills)
            call.respondText("OK")
        }
        post("/updateSkills") {
            val skills = call.receive<List<String>>()
            firebaseRepo.saveVolunteerSkills(skills)
            call.respond(skills)
        }
    }

}
