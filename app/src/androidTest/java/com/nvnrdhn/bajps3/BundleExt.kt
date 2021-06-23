package com.nvnrdhn.bajps3

import android.os.Bundle
import org.mockito.ArgumentMatcher
import org.mockito.internal.matchers.ContainsExtraTypeInfo
import org.mockito.internal.matchers.text.ValuePrinter
import java.util.*


class BundleEquals(private val expected: Bundle?) :
    ArgumentMatcher<Any?>,
    ContainsExtraTypeInfo {

    override fun matches(actual: Any?): Boolean {
        if (expected == null && actual == null) {
            return true
        }
        return if (expected == null || actual == null) {
            false
        } else areBundlesEqual(expected, actual as Bundle)
    }

    private fun areBundlesEqual(expected: Bundle, actual: Bundle): Boolean {
        if (expected.size() != actual.size()) {
            return false
        }
        if (!expected.keySet().containsAll(actual.keySet())) {
            return false
        }
        for (key in expected.keySet()) {
            val expectedValue = expected[key]
            val actualValue = actual[key]
            if (expectedValue is Bundle && actualValue is Bundle) {
                if (!areBundlesEqual(expectedValue, actualValue)) {
                    return false
                }
            } else if (!Objects.equals(expectedValue, actualValue)) {
                return false
            }
        }
        return true
    }

    override fun toStringWithType(): String {
        val clazz = expected?.javaClass?.simpleName
        return "(" + clazz + ") " + describe(expected)
    }

    private fun describe(`object`: Any?): String {
        return ValuePrinter.print(`object`)
    }

    override fun typeMatches(actual: Any): Boolean {
        return expected != null && actual != null && actual.javaClass == expected.javaClass
    }

    override fun toString(): String {
        return describe(expected)
    }
}