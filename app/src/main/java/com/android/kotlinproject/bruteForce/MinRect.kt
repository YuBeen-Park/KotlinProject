package com.android.kotlinproject.bruteForce

import java.util.*

fun main(){
    print(MinRect().solution(arrayOf(intArrayOf(60, 50), intArrayOf(30, 70), intArrayOf(60, 30), intArrayOf(80, 40))))

}
class MinRect {
    fun solution(sizes: Array<IntArray>): Int {
        var answer: Int = 0
        val pqMin = PriorityQueue<Int>(Collections.reverseOrder())
        val pqMax = PriorityQueue<Int>(Collections.reverseOrder())
        for (i in sizes.indices){
            if(sizes[i][0] > sizes[i][1]){
                pqMax.add(sizes[i][0])
                pqMin.add(sizes[i][1])
            } else {
                pqMax.add(sizes[i][1])
                pqMin.add(sizes[i][0])
            }
        }
        answer = pqMax.first()* pqMin.first()
        return answer
    }
}