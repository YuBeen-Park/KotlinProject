package com.android.kotlinproject.queue

fun main(){

}
class Truck {
    fun solution(bridge_length: Int, weight: Int, truck_weights: IntArray): Int{
        // 다리 길이만큼의 시간이 소요됨
        var answer = 0 //소요된 시간
        var curWeight = 0
        val dq = ArrayDeque<Int>()
        for(i in truck_weights.indices){
            val curTruck = truck_weights[i]
            while(true){
                if(dq.isEmpty()){
                    dq.add(curTruck)
                    curWeight += curTruck
                    answer++
                    break
                } else if( dq.size == bridge_length){
                    // 큐에 최대 트럭이 올라간 경우 트럭을 다리에서 내리기
                    curWeight -= dq.removeFirst()
                } else {
                    // 트럭이 올라갈 수 있는 경우
                    if(curWeight + curTruck <= weight){
                        dq.add(curTruck)
                        curWeight += curTruck
                        answer++
                        break
                    } else{
                        dq.add(0)
                        answer++
                    }
                }
            }
        }
        return answer + bridge_length
    }
}