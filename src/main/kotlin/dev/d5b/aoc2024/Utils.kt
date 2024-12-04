package dev.d5b.aoc2024

import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path

object Utils {

    private const val URL_ROOT = "https://adventofcode.com/2024"

    fun getInput(day: Int): String {
        val url = URL("$URL_ROOT/day/$day/input")

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(url.toURI())
            .header("cookie", "session=${System.getProperty("session")}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun getTestInput(day: Int): String {
        val path = Path.of("testinputs", "$day.txt")

        return Files.readString(path).replace("\r", "")
    }
}
