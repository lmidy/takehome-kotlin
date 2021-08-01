package midy

import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.server.testing.* // ktlint-disable no-wildcard-imports
import org.junit.Test
import kotlin.test.* // ktlint-disable no-wildcard-imports

class UserRouteTests {
    @Test
    fun testGetUser() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/v1/users").apply {
                assertEquals(
                    """[
  {
    "id": 1,
    "firstname": "Rachelle",
    "lastname": "Haggerty",
    "email": "RachelleTHaggerty@rhyta.com"
  },
  {
    "id": 2,
    "firstname": "Daniel",
    "lastname": "Powell",
    "email": "dpowell@yahoo.com"
  },
  {
    "id": 3,
    "firstname": "Adolfo",
    "lastname": "Campbell",
    "email": "adolfo_campbell@hotmail.com"
  },
  {
    "id": 4,
    "firstname": "Robert",
    "lastname": "Castillo",
    "email": "rcast743@gmail.com"
  },
  {
    "id": 5,
    "firstname": "Young",
    "lastname": "Neace",
    "email": "yneng@aol.com"
  },
  {
    "id": 6,
    "firstname": "Rick",
    "lastname": "Bailey",
    "email": "rcbailey@yahoo.com"
  },
  {
    "id": 7,
    "firstname": "Martha",
    "lastname": "Harrison",
    "email": "mh23harrison@yahoo.com"
  },
  {
    "id": 8,
    "firstname": "Leah",
    "lastname": "Fleming",
    "email": "lfleming@hotmail.com"
  },
  {
    "id": 9,
    "firstname": "John",
    "lastname": "Robinson",
    "email": "jhrobinson@gmail.com"
  },
  {
    "id": 10,
    "firstname": "Rhonda",
    "lastname": "Bruno",
    "email": "rbruno02@comcast.com"
  }
]""",
                    response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testGetWorkedHours() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "v1/users/1/worked_hours").apply {
                assertEquals(
                    """[
  {
    "id": 1,
    "date": "2021-01-01",
    "hours": "3.90"
  },
  {
    "id": 1,
    "date": "2021-01-04",
    "hours": "4.13"
  },
  {
    "id": 1,
    "date": "2021-01-05",
    "hours": "6.25"
  },
  {
    "id": 1,
    "date": "2021-01-06",
    "hours": "5.32"
  },
  {
    "id": 1,
    "date": "2021-01-07",
    "hours": "4.93"
  },
  {
    "id": 1,
    "date": "2021-01-08",
    "hours": "9.30"
  }
]""",
                    response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
