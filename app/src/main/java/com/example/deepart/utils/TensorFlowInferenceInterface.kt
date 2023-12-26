//package com.example.deepart.utils
//
//import android.content.res.AssetManager
//import android.os.Trace
//import android.text.TextUtils
//import android.util.Log
//import java.io.FileInputStream
//import java.io.IOException
//import java.io.InputStream
//import java.nio.ByteBuffer
//import java.nio.DoubleBuffer
//import java.nio.FloatBuffer
//import java.nio.IntBuffer
//import java.util.ArrayList
//import org.tensorflow.lite.DataType
//import org.tensorflow.lite.Tensor
//
//
//class TensorFlowInferenceInterface(var1: AssetManager, var2: String) {
//    private val modelName: String
//    private val g: Graph
//    private val sess: Session
//    private var runner: Session.Runner
//    private val feedNames: MutableList<String?> = ArrayList<Any?>()
//    private val feedTensors: MutableList<Tensor?> = ArrayList<Any?>()
//    private val fetchNames: MutableList<String?> = ArrayList<Any?>()
//    private var fetchTensors: MutableList<Tensor?> = ArrayList<Any?>()
//    private var runStats: RunStats? = null
//
//    init {
//        Log.i("TensorFlowInferenceInterface", "Checking to see if TensorFlow native methods are already loaded")
//        try {
//            RunStats()
//            Log.i("TensorFlowInferenceInterface", "TensorFlow native methods already loaded")
//        } catch (var10: UnsatisfiedLinkError) {
//            Log.i("TensorFlowInferenceInterface", "TensorFlow native methods not found, attempting to load via tensorflow_inference")
//            try {
//                System.loadLibrary("tensorflow_inference")
//                Log.i("TensorFlowInferenceInterface", "Successfully loaded TensorFlow native methods (RunStats error may be ignored)")
//            } catch (var9: UnsatisfiedLinkError) {
//                throw RuntimeException("Native TF methods not found; check that the correct native libraries are present in the APK.")
//            }
//        }
//        modelName = var2
//        g = Graph()
//        sess = Session(g)
//        runner = sess.runner()
//        val var3 = var2.startsWith("file:///android_asset/")
//        var var4: Any? = null
//        var4 = try {
//            val var5 = if (var3) var2.split("file:///android_asset/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] else var2
//            var1.open(var5)
//        } catch (var11: IOException) {
//            if (var3) {
//                throw RuntimeException("Failed to load model from '$var2'", var11)
//            }
//            try {
//                FileInputStream(var2)
//            } catch (var8: IOException) {
//                throw RuntimeException("Failed to load model from '$var2'", var11)
//            }
//        }
//        try {
//            loadGraph(var4 as InputStream?, g)
//            (var4 as InputStream?)!!.close()
//            Log.i("TensorFlowInferenceInterface", "Successfully loaded model from '$var2'")
//        } catch (var7: IOException) {
//            throw RuntimeException("Failed to load model from '$var2'", var7)
//        }
//    }
//
//    @JvmOverloads
//    fun run(var1: Array<String>, var2: Boolean = false) {
//        closeFetches()
//        val var4 = var1.size
//        for (var5 in 0 until var4) {
//            val var6 = var1[var5]
//            fetchNames.add(var6)
//            val var7 = TensorId.parse(var6)
//            runner.fetch(var7.name, var7.outputIndex)
//        }
//        try {
//            if (var2) {
//                val var13: Session.Run = runner.setOptions(RunStats.runOptions()).runAndFetchMetadata()
//                fetchTensors = var13.outputs
//                if (runStats == null) {
//                    runStats = RunStats()
//                }
//                runStats.add(var13.metadata)
//            } else {
//                fetchTensors = runner.run()
//            }
//        } catch (var11: RuntimeException) {
//            Log.e("TensorFlowInferenceInterface", "Failed to run TensorFlow inference with inputs:[" + TextUtils.join(", ", feedNames) + "], outputs:[" + TextUtils.join(", ", fetchNames) + "]")
//            throw var11
//        } finally {
//            closeFeeds()
//            runner = sess.runner()
//        }
//    }
//
//    fun graph(): Graph {
//        return g
//    }
//
//    fun graphOperation(var1: String): Operation {
//        val var2: Operation = g.operation(var1)
//        return if (var2 == null) {
//            throw RuntimeException("Node '" + var1 + "' does not exist in model '" + modelName + "'")
//        } else {
//            var2
//        }
//    }
//
//    val statString: String
//        get() = if (runStats == null) "" else runStats.summary()
//
//    fun close() {
//        closeFeeds()
//        closeFetches()
//        sess.close()
//        g.close()
//        if (runStats != null) {
//            runStats.close()
//        }
//        runStats = null
//    }
//
//    @Throws(Throwable::class)
//    protected fun finalize() {
//        try {
//            close()
//        } finally {
//            super.finalize()
//        }
//    }
//
//    fun feed(var1: String, var2: FloatArray?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, FloatBuffer.wrap(var2)))
//    }
//
//    fun feed(var1: String, var2: IntArray?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, IntBuffer.wrap(var2)))
//    }
//
//    fun feed(var1: String, var2: DoubleArray?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, DoubleBuffer.wrap(var2)))
//    }
//
//    fun feed(var1: String, var2: ByteArray?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(DataType.UINT8, var3, ByteBuffer.wrap(var2)))
//    }
//
//    fun feed(var1: String, var2: FloatBuffer?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, var2))
//    }
//
//    fun feed(var1: String, var2: IntBuffer?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, var2))
//    }
//
//    fun feed(var1: String, var2: DoubleBuffer?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(var3, var2))
//    }
//
//    fun feed(var1: String, var2: ByteBuffer?, vararg var3: Long) {
//        addFeed(var1, Tensor.create(DataType.UINT8, var3, var2))
//    }
//
//    fun fetch(var1: String, var2: FloatArray?) {
//        this.fetch(var1, FloatBuffer.wrap(var2))
//    }
//
//    fun fetch(var1: String, var2: IntArray?) {
//        this.fetch(var1, IntBuffer.wrap(var2))
//    }
//
//    fun fetch(var1: String, var2: DoubleArray?) {
//        this.fetch(var1, DoubleBuffer.wrap(var2))
//    }
//
//    fun fetch(var1: String, var2: ByteArray?) {
//        this.fetch(var1, ByteBuffer.wrap(var2))
//    }
//
//    fun fetch(var1: String, var2: FloatBuffer?) {
//        getTensor(var1).writeTo(var2)
//    }
//
//    fun fetch(var1: String, var2: IntBuffer?) {
//        getTensor(var1).writeTo(var2)
//    }
//
//    fun fetch(var1: String, var2: DoubleBuffer?) {
//        getTensor(var1).writeTo(var2)
//    }
//
//    fun fetch(var1: String, var2: ByteBuffer?) {
//        getTensor(var1).writeTo(var2)
//    }
//
//    @Throws(IOException::class)
//    private fun loadGraph(var1: InputStream?, var2: Graph) {
//        val var3 = System.currentTimeMillis()
//        Trace.beginSection("initializeTensorFlow")
//        Trace.beginSection("readGraphDef")
//        val var5 = ByteArray(var1!!.available())
//        val var6 = var1.read(var5)
//        if (var6 != var5.size) {
//            throw IOException("read error: read only " + var6 + " of the graph, expected to read " + var5.size)
//        } else {
//            Trace.endSection()
//            Trace.beginSection("importGraphDef")
//            try {
//                var2.importGraphDef(var5)
//            } catch (var9: IllegalArgumentException) {
//                throw IOException("Not a valid TensorFlow Graph serialization: " + var9.message)
//            }
//            Trace.endSection()
//            Trace.endSection()
//            val var7 = System.currentTimeMillis()
//            Log.i("TensorFlowInferenceInterface", "Model load took " + (var7 - var3) + "ms, TensorFlow version: " + TensorFlow.version())
//        }
//    }
//
//    private fun addFeed(var1: String, var2: Tensor) {
//        val var3 = TensorId.parse(var1)
//        runner.feed(var3.name, var3.outputIndex, var2)
//        feedNames.add(var1)
//        feedTensors.add(var2)
//    }
//
//    private fun getTensor(var1: String): Tensor? {
//        var var2 = 0
//        val var3: Iterator<*> = fetchNames.iterator()
//        while (var3.hasNext()) {
//            val var4 = var3.next() as String
//            if (var4 == var1) {
//                return fetchTensors[var2] as Tensor?
//            }
//            ++var2
//        }
//        throw RuntimeException("Node '$var1' was not provided to run(), so it cannot be read")
//    }
//
//    private fun closeFeeds() {
//        val var1: Iterator<*> = feedTensors.iterator()
//        while (var1.hasNext()) {
//            val var2: Tensor = var1.next() as Tensor
//            var2.close()
//        }
//        feedTensors.clear()
//        feedNames.clear()
//    }
//
//    private fun closeFetches() {
//        val var1: Iterator<*> = fetchTensors.iterator()
//        while (var1.hasNext()) {
//            val var2: Tensor = var1.next() as Tensor
//            var2.close()
//        }
//        fetchTensors.clear()
//        fetchNames.clear()
//    }
//
//    private class TensorId private constructor() {
//        var name: String? = null
//        var outputIndex = 0
//
//        companion object {
//            fun parse(var0: String): TensorId {
//                val var1 = TensorId()
//                val var2 = var0.lastIndexOf(58.toChar())
//                return if (var2 < 0) {
//                    var1.outputIndex = 0
//                    var1.name = var0
//                    var1
//                } else {
//                    try {
//                        var1.outputIndex = var0.substring(var2 + 1).toInt()
//                        var1.name = var0.substring(0, var2)
//                    } catch (var4: NumberFormatException) {
//                        var1.outputIndex = 0
//                        var1.name = var0
//                    }
//                    var1
//                }
//            }
//        }
//    }
//
//    companion object {
//        private const val TAG = "TensorFlowInferenceInterface"
//        private const val ASSET_FILE_PREFIX = "file:///android_asset/"
//    }
//}
