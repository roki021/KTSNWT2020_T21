package com.culturaloffers.maps;

import com.culturaloffers.maps.controllers.CommentsControllerIntegrationTest;
import com.culturaloffers.maps.controllers.GradesControllerIntegrationTest;
import com.culturaloffers.maps.controllers.SubscriptionControllerIntegrationTest;
import com.culturaloffers.maps.repositories.*;
import com.culturaloffers.maps.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({GradeRepositoryIntegrationTest.class, GradesServiceIntegrationTest.class, GradesControllerIntegrationTest.class,
        CommentRepositoryIntegrationTest.class, CommentsServiceIntegrationTest.class, CommentsControllerIntegrationTest.class,
        SubscriptionServiceIntegrationTest.class, SubscriptionControllerIntegrationTest.class

})
@TestPropertySource("classpath:test-grades.properties")
public class SuiteAN {
}
