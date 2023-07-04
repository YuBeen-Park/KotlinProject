package com.android.kotlinproject.queue

import java.util.*

fun main() {
    println(Solution().solution(intArrayOf(2, 1, 3, 2), 2))
    println(Solution().solution(intArrayOf(1, 1, 9, 1, 1, 1), 0))
}

class Solution {
    fun solution(priorities: IntArray, location: Int): Int {
        var answer = 0
        val pq = PriorityQueue<Int>(Collections.reverseOrder())// 우선순위 내림차순 정렬
        pq.addAll(priorities.toTypedArray())
        while (pq.isNotEmpty()) {
            for (i in priorities.indices) {
                if (priorities[i] == pq.peek()) {
                    if (i == location) {
                        answer++
                        return answer
                    }
                    pq.poll() //삭제
                    answer++
                }
            }
        }
        return 0
    }
}