package a1klik.csp203.a1klik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class after_login extends AppCompatActivity {
    private ListView lvcourse;
    private courseListAdapter adapter;
    private List<course> mCourseList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
        String data= getIntent().getStringExtra("MyData");
        final String user_name=get_name(data);
        final int instructor_id=get_id(data);
        set_header(user_name);
        try {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            String type = "get_course";
            String id = Integer.toString(instructor_id);
            String output = backgroundWorker.execute(type, id).get();
            final TextView tv_new = (TextView) findViewById(R.id.waitShowScreen);
            courseData courseExracted=new courseData(output,'#');
            lvcourse=(ListView)findViewById(R.id.courseListView);
            mCourseList=new ArrayList<>();
            int i=1;
            while (courseExracted.hasNext())
            {
                String[] splited=courseExracted.getNextLine().split(" ");
                String course_name=splited[1];
                int courseID=Integer.valueOf(splited[0]);
                String course_title="";
                try {
                    for (int delete_this_tmp = 5; delete_this_tmp < splited.length; delete_this_tmp++)
                    {
                        course_title=course_title+" "+splited[delete_this_tmp];
                    }
                    //System.out.println("COURSE NAME : "+course_title);
                }
                catch(Exception ex)
                {
                    ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
                    useThis.loadNewActivity();
                    ex.getMessage();
                    startActivity(getIntent());
                    ex.printStackTrace();

                }
                //System.out.println("COURSE NAME : "+course_title);
                //;
                mCourseList.add(new course(course_name,courseID,course_title,i));
                //tv_new.setText(tv_new.getText().toString()+"\n"+course_name);
                i++;
            }
            adapter=new courseListAdapter(getApplicationContext(),mCourseList);
            lvcourse.setAdapter(adapter);
            tv_new.setText("Your Courses("+Integer.toString(i-1)+")");
            lvcourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //tv_new.setText("Clicked Course");
                    Intent newCoursePage=new Intent(after_login.this,coursePage.class);
                    newCoursePage.putExtra("course_id", mCourseList.get((int)id).getCourseID());
                    newCoursePage.putExtra("id", mCourseList.get((int)id).getId());
                    newCoursePage.putExtra("courseName",mCourseList.get((int)id).getCourseName());
                    newCoursePage.putExtra("courseTitle", mCourseList.get((int)id).getCourseTitle());
                    newCoursePage.putExtra("UserName", user_name);
                    newCoursePage.putExtra("Instructor_id",instructor_id);
                    startActivity(newCoursePage);
                    Toast.makeText(getApplicationContext(), "Clicked Course " + view.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (InterruptedException e)
        {
            ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
            useThis.loadNewActivity();
            e.getMessage();
            startActivity(getIntent());
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
            useThis.loadNewActivity();
            e.getMessage();
            startActivity(getIntent());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            ExceptionHandlerRedirector useThis=new ExceptionHandlerRedirector();
            useThis.loadNewActivity();
            e.getMessage();
            startActivity(getIntent());
            e.printStackTrace();
        }

    }
    void onLogout(View view)
    {

    }
    public int get_id(String input)
    {
        String output="";
        int i=0;
        int n=input.length();
        while (i<n && Character.isDigit(input.charAt(i))==true)
        {
            output=output+input.charAt(i);
            i++;
        }
        return Integer.valueOf(output);
    }
    public String get_name(String input)
    {
        String output="";
        int i=0;
        int n=input.length();
        while (i<n && Character.isDigit(input.charAt(i))==true)
        {
            i++;
        }
        while (i<n)
        {
            output=output+input.charAt(i);
            i++;
        }
        return output;
    }
    public void set_header(String input)
    {
        TextView tv = (TextView) findViewById(R.id.loginUserName);
        tv.setText(input);
        return;
    }

}
