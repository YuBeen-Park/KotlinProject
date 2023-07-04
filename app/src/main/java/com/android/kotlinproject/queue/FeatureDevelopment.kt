package com.android.kotlinproject.queue

fun main(){
    println(FeatureDevelopment().solution(intArrayOf(93, 30, 55), intArrayOf(1, 30, 5)).contentToString())
    println(FeatureDevelopment().solution(intArrayOf(95, 90, 99, 99, 80, 99), intArrayOf(1,1,1,1,1,1)).contentToString())
}
class FeatureDevelopment {
    fun solution(progresses: IntArray, speeds: IntArray): IntArray {
        val answer = ArrayList<Int>()
        val deq = ArrayDeque<Int>()
        for (i in progresses.indices) {
            var finishDate = (100 - progresses[i]) / speeds[i]
            if ((100 - progresses[i]) % speeds[i] > 0)
                finishDate++
            deq.add(finishDate)
        }

        while (deq.isNotEmpty()) {
            var cur = 0
            val curDate = deq.first()
            for (i in 0 until deq.size) {
                if (curDate >= deq[i])
                    cur++
                else
                    break
            }
            answer.add(cur)
            for (i in 1..cur) {
                deq.removeFirst()
            }
        }

        return answer.toIntArray()
    }
}