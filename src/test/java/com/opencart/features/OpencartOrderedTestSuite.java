package com.opencart.features;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OpencartMacbookTest.class,
        OpencartIphoneTest.class,
        OpencartCamionetaNegativeTest.class
})
public class OpencartOrderedTestSuite {
}