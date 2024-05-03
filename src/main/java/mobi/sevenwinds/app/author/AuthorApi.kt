package mobi.sevenwinds.app.author

import com.papsign.ktor.openapigen.annotations.type.string.length.MinLength
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import jdk.jfr.Timestamp
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType


fun NormalOpenAPIRoute.author() {
    route("/author") {
        route("/add").post<Unit, AuthorRecord, AuthorRecord>(info("Добавить автора")) { _, body ->
            respond(AuthorService.addAuthor(body))
        }
    }
}

data class AuthorRecord(
    @MinLength(1) val fullName: String,
    val creationDate: DateTime = DateTime.now()
)