package com.marathon.ktm.view

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Camera
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Sun
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.marathon.ktm.R
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var arFragment: ArFragment
    private var defaultUri = Uri.parse("pizza.sfb")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }

        setContentView(R.layout.activity_menu)
        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()
            cleanScreen()
            placeObject(arFragment, anchor, defaultUri)

        }
        iv_burger.setOnClickListener(this)
        iv_lasagne.setOnClickListener(this)
        iv_pizza.setOnClickListener(this)
    }

    private fun placeObject(arFragment: ArFragment, anchor: Anchor, uri: Uri) {
        ModelRenderable.builder()
            .setSource(arFragment.context, uri)
            .build()
            .thenAccept { modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable) }
            .exceptionally { throwable ->
                Toast.makeText(arFragment.context, "Error:" + throwable.message, Toast.LENGTH_LONG)
                    .show()
                null
            }

    }

    private fun addNodeToScene(arFragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.scaleController.maxScale = 0.10f
        node.scaleController.minScale = 0.05f
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            iv_burger.id -> {
                defaultUri = Uri.parse("hamburger.sfb")
            }
            iv_lasagne.id -> {
                defaultUri = Uri.parse("Ramen.sfb")
            }
            iv_pizza.id -> {
                defaultUri = Uri.parse("pizza.sfb")
            }
        }
    }

    private fun cleanScreen() {
        val children: List<Node> = ArrayList(arFragment.arSceneView.scene.children)
        for (node in children) {
            if (node is AnchorNode) {
                if (node.anchor != null) {
                    node.anchor!!.detach()
                }
            }

        }
    }

    private fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e("TAG", "Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < 3.0) {
            Log.e("TAG", "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }
     companion object{
         fun newInstance(activity: Activity){
             val myIntent = Intent(activity, MenuActivity::class.java)
           activity.startActivity(myIntent)
         }
     }
}