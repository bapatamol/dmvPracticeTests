package controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import controller.strategy.QuestionCallBack;
import controller.strategy.QuestionSourceStrategy;
import model.QuestionData;

/**
 * Created by abapat on 4/9/2015.
 */
public class RandomQuestionSourceStrategy  implements QuestionSourceStrategy {

    private int randomQuestionNumber = 0;
    private ArrayList<QuestionData> allQuestions;

    public void readyQuestions() {
        Log.d("DMV", "RandomQuestionSourceStrategy readyQuestions()");
        allQuestions = new ArrayList<>();
        loadQuestionTexts();
        loadAnswers();
        loadChoice1s();
        loadChoice2s();
        loadChoice3s();
        loadChoice4s();


        QuestionData q;
        for (int i = 1; i < 48; i++) {
            q = new QuestionData();
            q.setQuestionText(questionTexts.get(i));
            q.setChoice1(choice1s.get(i));
            q.setChoice2(choice2s.get(i));
            q.setChoice3(choice3s.get(i));
            q.setChoice4(choice4s.get(i));
            q.setAnswer(answers.get(i));
            allQuestions.add(q);
        }
    }


    @Override
    public void invalidateRequest() {

    }

    @Override
    public void requestNextQuestion(QuestionCallBack qcb, Context context) {
        int i = getRandomQuestionNumber();
        Log.d("DMV", "Getting question number - " + i);
        qcb.hereIsTheQuestion(allQuestions.get(i));
    }

    public int getRandomQuestionNumber() {
        generateRandomQuestion();
        return randomQuestionNumber;
    }

    private void generateRandomQuestion() {
        Random r = new Random();
        randomQuestionNumber = r.nextInt(questionCount());
    }

    @Override
    public void requestPreviousQuestion(QuestionCallBack qcb, Context context) {
        int i = getRandomQuestionNumber();
        Log.d("DMV", "Getting question number - " + i);
        qcb.hereIsTheQuestion(allQuestions.get(i));
    }

    public int questionCount() {
        return 47;
    }

    private ArrayList<String> questionTexts = new ArrayList<>();
    private ArrayList<String> choice1s = new ArrayList<>();
    private ArrayList<String> choice2s = new ArrayList<>();
    private ArrayList<String> choice3s = new ArrayList<>();
    private ArrayList<String> choice4s = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    public void loadQuestionTexts() {
        questionTexts.add("When you find both a solid and a dashed yellow line between opposing lanes of traffic, you must not pass:");
        questionTexts.add("The traffic striping marks of diagonal yellow lines are on streets and highways. These indicate:");
        questionTexts.add("If a pedestrian is in a marked or unmarked crosswalk, you must:");
        questionTexts.add("Flashing arrow panels may be used in work zones to:");
        questionTexts.add("When you drive in city traffic, you should try to look at least:");
        questionTexts.add("Proactively looking ahead, to the sides, and behind the vehicle to anticipate problems ahead is part of:");
        questionTexts.add("On some roadways, some lanes reserved as 'Transit' are used for:");
        questionTexts.add("If you see a vehicle coming toward you in your lane from the opposite direction, what should you do?");
        questionTexts.add("If you double your speed on a highway, your braking distance increases by");
        questionTexts.add("A flashing yellow arrow means:");
        questionTexts.add("For interstate driving, slow-moving vehicles must travel _________, except when passing.");
        questionTexts.add("When you see a triangle-shaped road sign while driving, you must:");
        questionTexts.add("When approaching pedestrians who are walking on or crossing the roadway, you must:");
        questionTexts.add("If you want to cross several lanes in a multi-lane highway:");
        questionTexts.add("If a driver behind you is following too closely, you must:");
        questionTexts.add("If you are changing lanes, preparing to pass another vehicle, or entering traffic, signal and check for passing traffic by first using your mirrors and then:");
        questionTexts.add("Black on orange signs on a highway identify:");
        questionTexts.add("When should you switch on your high-beam headlights?");
        questionTexts.add("Additional fines will be charged if you are caught driving ____mph or more over the posted limit.");
        questionTexts.add("Fines will _____ if you speed in marked construction zones.");
        questionTexts.add("If you are caught driving in excess of _____ mph your driving privileges will be revoked for a minimum of six months.");
        questionTexts.add("Hand and arm signals:");
        questionTexts.add("While making a turn, show your intent by indicating at least _____ feet before the turn");
        questionTexts.add("U turns are illegal:");
        questionTexts.add("Do not attempt to pass another vehicle in locations");
        questionTexts.add("If a vehicle has a passenger airbag, it is essential for children ____ years of age and under to ride in the backseat.");
        questionTexts.add("Children under _______ years of age must use a federally approved, properly installed child safety restraint system.");
        questionTexts.add("Infants must ride in a rear - facing child safety restraint system until they are at least ____year of age and weigh at least 20pounds.");
        questionTexts.add("It is the driver’s responsibility");
        questionTexts.add("Every driver who is involved in a crash that results in injury, death, or property damage of $1, 000or more must file a traffic crash report within ____ days of the incident.");
        questionTexts.add("A person may use wireless communication device:");
        questionTexts.add("When a school bus is stopped with its red lights flashing and its stop arm extended, you must stop your vehicle at least ___ feet from the bus");
        questionTexts.add("You are not required to stop for a school bus with its red lights flashing if ");
        questionTexts.add("A commercial vehicle is a motor vehicle or combination of motor vehicles weighing more than pounds that is used to transport passengers or property.");
        questionTexts.add("Regulatory signs are:");
        questionTexts.add("Warnings signs are ");
        questionTexts.add("Informational signs are");
        questionTexts.add("Brown signs point out");
        questionTexts.add("Blue signs point out");
        questionTexts.add("Green signs indicate");
        questionTexts.add("SIPDE is a 5 - step process used to make appropriate judgments and apply them correctly in different traffic situations.SIPDE stands for:");
        questionTexts.add("If a load extends____ feet or more from the rear of any vehicle, a red, yellow, or orange flag at least 16inches square must be attached to the end of the load.");
        questionTexts.add("If you pull a camper or trailer with your vehicle, you must maintain at least _____ feet following distance from other vehicles.");
        questionTexts.add("You must be at least ____years of age to drive a recreational vehicle combination");
        questionTexts.add("At higher speeds, water can collect under tires and lift them off the surface of the road — this is called __________ ");
        questionTexts.add("A “blowout” is a burst tire that can throw your vehicle out of control. If you experience a blowout:");
        questionTexts.add("Crashes involving impaired drivers kill an average of ____ people each year in Minnesota and injure thousands of others.");
        questionTexts.add("A person with an alcohol concentration of ___or higher who is in control of a moving or parked vehicle, can be arrested for driving while impaired(DWI).");
    }
    public void loadChoice1s() {
        choice1s.add("if the solid yellow line is not on your side");
        choice1s.add("The road is narrowing");
        choice1s.add("reduce your speed and proceed with caution");
        choice1s.add("stop all vehicles near work zone");
        choice1s.add("3 blocks ahead");
        choice1s.add("defensive driving");
        choice1s.add("trucks only");
        choice1s.add("You must slow down and sound your horn");
        choice1s.add("3 times");
        choice1s.add("you must not proceed in the direction of arrow");
        choice1s.add("in the middle lane");
        choice1s.add("make a right turn");
        choice1s.add("slow down, yield and be prepared to stop");
        choice1s.add("take them with reduced speed");
        choice1s.add("move to the left lane and increase your speed");
        choice1s.add("by checking the oncoming traffic");
        choice1s.add("traffic laws and regulations");
        choice1s.add("When you drive in construction areas");
        choice1s.add("10");
        choice1s.add("double");
        choice1s.add("70");
        choice1s.add("during day light in addition to, or instead of, turn signals.");
        choice1s.add("100");
        choice1s.add("near the tops of hills");
        choice1s.add("where a “No Passing Zone” sign is posted");
        choice1s.add("12");
        choice1s.add("4");
        choice1s.add("1");
        choice1s.add("to make sure that other passengers are safe.");
        choice1s.add("reasonable");
        choice1s.add("to send messages");
        choice1s.add("20");
        choice1s.add("it is on the opposite side of a separated roadway.");
        choice1s.add("10000");
        choice1s.add("Red and white");
        choice1s.add("Yellow and Orange");
        choice1s.add("Green Blue Brown");
        choice1s.add("historical signs");
        choice1s.add("services such as telephone gas, foods, motels, hospitals and rest areas");
        choice1s.add("Interstate Exits");
        choice1s.add("Scan • Identify • Predict • Decide • Execute");
        choice1s.add(" four");
        choice1s.add("500");
        choice1s.add("16");
        choice1s.add("hydroplaning");
        choice1s.add("hold the steering wheel tightly, steer straight ahead, and slowly ease your foot off the accelerator.");
        choice1s.add("240");
        choice1s.add("0.08");

    }
    public void loadChoice2s() {
        choice2s.add("if the dashed yello line is on your side");
        choice2s.add("there is an obstruction on the roadway");
        choice2s.add("stop and wait until pedestrian has passed");
        choice2s.add("indicate the permissible speed limit");
        choice2s.add("2 blocks ahead");
        choice2s.add("agressive driving");
        choice2s.add("bicycles only");
        choice2s.add("You must flash your headlights");
        choice2s.add("5 times");
        choice2s.add("you may proceed with caution in the direction of arrow");
        choice2s.add("in the left hand lane");
        choice2s.add("come to a compelete stop");
        choice2s.add("accelerate and move quickly");
        choice2s.add("take them with higher speed");
        choice2s.add("move to the left lane and reduce your speed");
        choice2s.add("by checking the front of your vehicle");
        choice2s.add("specific information and directions to drivers in worker zones");
        choice2s.add("When you drive on unfamiliar roads");
        choice2s.add("20");
        choice2s.add("reduce");
        choice2s.add("80");
        choice2s.add("may not be used during the night");
        choice2s.add("200");
        choice2s.add("on curves where other drivers cannot see you from 1,000 feet away.");
        choice2s.add("where there is a solid yellow line on your side of the center line. ");
        choice2s.add("11");
        choice2s.add("5");
        choice2s.add("6 months ");
        choice2s.add("all answers are correct");
        choice2s.add("10");
        choice2s.add("to call");
        choice2s.add("100");
        choice2s.add("the road is not separated");
        choice2s.add("15000");
        choice2s.add("Yellow");
        choice2s.add("Red and white");
        choice2s.add("Red and white");
        choice2s.add("cultural signs");
        choice2s.add("cultural signs");
        choice2s.add("Distances to cities");
        choice2s.add("See • Identify • Predict • Decide • Execute");
        choice2s.add("five ");
        choice2s.add("1000");
        choice2s.add("18");
        choice2s.add("skidding");
        choice2s.add("Pull the vehicle completely off the road at the nearest safe location.");
        choice2s.add("2000");
        choice2s.add("0.04");

    }
    public void loadChoice3s() {
        choice3s.add("if the solid yellow line is on your side ");
        choice3s.add("either A or B");
        choice3s.add("not stop your vehicle");
        choice3s.add("direct vehicles into parking areas");
        choice3s.add("4 blocks ahead");
        choice3s.add("disciplined driving");
        choice3s.add("HOV only");
        choice3s.add("You must pull as far to the right as you safely can");
        choice3s.add("four times");
        choice3s.add("you may go straight ahead");
        choice3s.add("in the right hand lane ");
        choice3s.add("increase your speed");
        choice3s.add("blow your horn, slow down and stop");
        choice3s.add("take them one at a time");
        choice3s.add("move to the right lane and stop your vehicle");
        choice3s.add("by checking the back of your vehicle");
        choice3s.add("special cconditions or hazard ahead");
        choice3s.add("where there are may be people along the side of the road");
        choice3s.add("30");
        choice3s.add("triple");
        choice3s.add("100");
        choice3s.add("may not be used while driving a vehicle constructed or loaded so that hand signals are not visible");
        choice3s.add("400");
        choice3s.add("No U-Turn” signs are posted");
        choice3s.add("Double solid yellow lines");
        choice3s.add("10");
        choice3s.add("2");
        choice3s.add("18 months");
        choice3s.add("Insist that all passengers wear seat belts");
        choice3s.add("7");
        choice3s.add("to email");
        choice3s.add("200");
        choice3s.add("N/A");
        choice3s.add("26000");
        choice3s.add("Orange");
        choice3s.add("Blue ");
        choice3s.add("Yellow");
        choice3s.add("recreational signs");
        choice3s.add("guidance and information signs");
        choice3s.add("interstate interchanges, street or route names, and bicycle routes.");
        choice3s.add("See • Identify • Predict • Decide • Evaluate");
        choice3s.add("two");
        choice3s.add("200");
        choice3s.add("21");
        choice3s.add("brake failure");
        choice3s.add("Do not brake until the vehicle is back under your control.");
        choice3s.add("1600");
        choice3s.add("0.06");

    }
    public void loadChoice4s() {
        choice4s.add("None of the above");
        choice4s.add("neither A nor B");
        choice4s.add("increase your speed and cross the crosswalk quickly");
        choice4s.add("guide drivers into correct lanes");
        choice4s.add("1 block ahead");
        choice4s.add("distracted driving");
        choice4s.add("buses only");
        choice4s.add("All of the above");
        choice4s.add("two times");
        choice4s.add("you must come to full stop in direction of arrow");
        choice4s.add("in the shoulder lane");
        choice4s.add("reduce your speed and yield");
        choice4s.add("use emergency lights");
        choice4s.add("take them 3 at a time");
        choice4s.add("move to the right lane and reduce your speed");
        choice4s.add("by checking your vehicle's blind spot");
        choice4s.add("directions routes and distances");
        choice4s.add("All of the above");
        choice4s.add("35");
        choice4s.add("not be charged");
        choice4s.add("90");
        choice4s.add("All of the above");
        choice4s.add("300");
        choice4s.add("All of the above");
        choice4s.add("All of the above");
        choice4s.add("13");
        choice4s.add("3");
        choice4s.add("2");
        choice4s.add("Be sure that children are buckled into an age-appropriate child passenger restraint system.");
        choice4s.add("30");
        choice4s.add("None of the above");
        choice4s.add("50");
        choice4s.add("N/A");
        choice4s.add("25000");
        choice4s.add("Green");
        choice4s.add("Brown");
        choice4s.add("Orange");
        choice4s.add("All of the above");
        choice4s.add("All of the above");
        choice4s.add("All of the above");
        choice4s.add("Scan• Identify • Predict • Decide • Evaluate");
        choice4s.add("six");
        choice4s.add("300");
        choice4s.add("25");
        choice4s.add("None of the above");
        choice4s.add("All of the above");
        choice4s.add("800");
        choice4s.add("0.01");

    }
    public void loadAnswers() {
        answers.add("3");
        answers.add("3");
        answers.add("2");
        answers.add("4");
        answers.add("4");
        answers.add("1");
        answers.add("4");
        answers.add("4");
        answers.add("3");
        answers.add("2");
        answers.add("3");
        answers.add("4");
        answers.add("1");
        answers.add("3");
        answers.add("4");
        answers.add("4");
        answers.add("2");
        answers.add("4");
        answers.add("2");
        answers.add("1");
        answers.add("3");
        answers.add("4");
        answers.add("1");
        answers.add("4");
        answers.add("4");
        answers.add("1");
        answers.add("1");
        answers.add("1");
        answers.add("2");
        answers.add("2");
        answers.add("4");
        answers.add("1");
        answers.add("1");
        answers.add("3");
        answers.add("1");
        answers.add("1");
        answers.add("1");
        answers.add("4");
        answers.add("1");
        answers.add("4");
        answers.add("1");
        answers.add("1");
        answers.add("1");
        answers.add("2");
        answers.add("1");
        answers.add("4");
        answers.add("1");
        answers.add("1");
    }

}
