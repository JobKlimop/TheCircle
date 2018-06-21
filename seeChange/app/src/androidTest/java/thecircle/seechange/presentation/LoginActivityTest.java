package thecircle.seechange.presentation;

import android.app.Application;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;


import thecircle.seechange.presentation.activities.LoginActivity;


// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginActivityTest extends Application {

    public String TestUsername;
    public String TestPassword;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private LoginActivity loginActivity;
    @Test
    public void onCreate() {

    }

    @Test
    public void testLoginSuccess() throws Exception {
        TestUsername = "mika";
        TestPassword = "test";
        assertThat( mUsernameView.getText().toString(), is(TestUsername));
        assertThat( mPasswordView.getText().toString(), is(TestPassword));
    }
//    @Test
//    public void changeText_sameActivity() {
//
//        // Type text and then press the button.
//        onView(withId(R.id.mUsernameView))
//                .perform(typeText(TestUsername), closeSoftKeyboard());
//        onView(withId(R.id.mPasswordView))
//                .perform(typeText(TestPassword), closeSoftKeyboard());
//        onView(withId(R.id.mSignInButton)).perform(click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(TestUsername)));
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(TestPassword)));
//    }

}