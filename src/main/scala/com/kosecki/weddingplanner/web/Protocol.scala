package com.kosecki.weddingplanner.web

import com.kosecki.weddingplanner.web.Service.{Guest, GuestCreated}
import spray.json.DefaultJsonProtocol


trait Protocol extends DefaultJsonProtocol {
  implicit val guestFormat = jsonFormat2(Guest.apply)
  implicit val guestCreatedFormat = jsonFormat1(GuestCreated.apply)
}
