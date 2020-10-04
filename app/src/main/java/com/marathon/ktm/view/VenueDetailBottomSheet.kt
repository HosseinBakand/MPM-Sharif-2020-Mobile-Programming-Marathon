package com.marathon.ktm.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marathon.ktm.R
import kotlinx.android.synthetic.main.bottom_sheet_venue_detail.*


const val VENUE_DETAIL_TITLE_KEY = "venue_detail_title_key"
const val VENUE_DETAIL_ADDRESS_KEY = "venue_detail_address_key"
const val VENUE_DETAIL_ADDRESS_ICON_URL = "venue_detail_icon_url_key"

class VenueDetailBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_venue_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_sheet_title.text = arguments?.getString(VENUE_DETAIL_TITLE_KEY)
        bottom_sheet_address.text = arguments?.getString(VENUE_DETAIL_ADDRESS_KEY)
        val sLat = arguments?.getString("slat")
        val sLng = arguments?.getString("slng")
        val dLat = arguments?.getString("dlat")
        val dLng = arguments?.getString("dlng")
        Glide.with(this)
            .load(arguments?.getString(VENUE_DETAIL_ADDRESS_ICON_URL))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(bottom_sheet_icon)

        btn_navigation.setOnClickListener {
            val uri =
                "http://maps.google.com/maps?saddr=" + "${sLat},${sLng}" + "&daddr=" + "${dLat},${dLng}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): VenueDetailBottomSheet {
            val fragment = VenueDetailBottomSheet()
            fragment.arguments = bundle
            return fragment
        }
    }

}