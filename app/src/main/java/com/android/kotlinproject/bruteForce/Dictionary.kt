package com.android.kotlinproject.bruteForce

fun main(){
    println(Dictionary().solution("EIO"))
}
class Dictionary {
    // 프로그래머스 모음사전
    val sol = arrayOf(781, 156, 31, 6, 1)
    val str = "AEIOU"
    fun solution(word: String): Int {
        var answer = word.length
        for (i in word.indices){
            answer += str.indexOf(word[i]) * sol[i]
        }
        return answer
    }
}