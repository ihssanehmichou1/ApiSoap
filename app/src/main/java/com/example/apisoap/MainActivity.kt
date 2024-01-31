package com.example.apisoap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.InputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResult = findViewById(R.id.text)

        try {
            val fileName = "file.xml"
            val inputStream: InputStream = assets.open(fileName)
            val doc: Document = parseXml(inputStream)
            displayProductDetails(doc)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseXml(inputStream: InputStream): Document {
        val dbFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val dBuilder: DocumentBuilder = dbFactory.newDocumentBuilder()
        return dBuilder.parse(inputStream).apply {
            documentElement.normalize()
        }
    }

    private fun displayProductDetails(doc: Document) {
        val nList: NodeList = doc.getElementsByTagName("product")

        for (i in 0 until nList.length) {
            val node: Node = nList.item(i)

            if (node.nodeType === Node.ELEMENT_NODE) {
                val element2: Element = node as Element
                val name = getValue("name", element2)
                val price = getValue("price", element2)

                textViewResult.append("\nNom : $name\n")
                textViewResult.append("Prix : $price\n")
                textViewResult.append("-----------------------")
            }
        }
    }

    private fun getValue(tag: String, element: Element): String? {
        val nodeList = element.getElementsByTagName(tag).item(0).childNodes
        val node = nodeList.item(0)
        return node.nodeValue
    }
}