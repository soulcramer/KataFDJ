package app.soulcramer.domain.test

import java.util.concurrent.ThreadLocalRandom

/**
 * Factory class for data instances
 */
class DataFactory {

    companion object Factory {

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }

        fun makeStringList(count: Int): List<String> {
            val items: MutableList<String> = mutableListOf()
            repeat(count) {
                items.add(randomUuid())
            }
            return items
        }

        fun makeSport(hasOtherSportThanSoccer: Boolean): String {
            return if (hasOtherSportThanSoccer) {
                if (randomBoolean()) {
                    "Soccer"
                } else {
                    randomUuid()
                }
            } else {
                "Soccer"
            }
        }
    }
}