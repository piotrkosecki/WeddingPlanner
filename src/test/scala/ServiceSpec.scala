import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kosecki.weddingplanner.web.Service.Guest
import com.kosecki.weddingplanner.web.{Protocol, Service}
import org.scalatest.{Matchers, WordSpec}


class ServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Service with Protocol {


  sealed trait Fixtures {
    val firstName   = "John"
    val lastName    = "Smith"
    val guest = Guest(firstName, lastName)
  }

  "Service" should {

    "create new guest" in new Fixtures {
      Post("/guests", guest) ~> routes ~> check {
        status shouldBe Created
        contentType shouldBe `application/json`
      }
    }

    "list guest" in new Fixtures {
      Get("/guests") ~> routes ~> check {
        status shouldBe OK
        contentType shouldBe `application/json`
      }
    }


  }
}