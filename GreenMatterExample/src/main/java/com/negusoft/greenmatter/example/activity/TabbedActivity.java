package com.negusoft.greenmatter.example.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.widget.DatePicker;

import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.example.fragment.ButtonFragment;
import com.negusoft.greenmatter.example.fragment.ChoicesFragment;
import com.negusoft.greenmatter.example.fragment.ListFragment;
import com.negusoft.greenmatter.example.fragment.PickersFragment;
import com.negusoft.greenmatter.example.fragment.ProgressFragment;
import com.negusoft.greenmatter.example.fragment.TextviewFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TabbedActivity extends MatActivity implements ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable d = getResources().getDrawable(R.drawable.abc_btn_check_to_on_mtrl_015);

		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);

			FragmentManager fragmentManager = getSupportFragmentManager();
			mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			
			configureTabs(actionBar, mViewPager);
		}
	}

//    @Override
//    public int getOverrideAccentColor() {
//        return ColorOverrideConfig.getColor();
//    }
	
	protected int getLayoutId() {
		return R.layout.activity_tabbed;
	}
	
	protected void configureTabs(final ActionBar actionBar, ViewPager viewPager) {
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			Tab newTab = actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this);
			actionBar.addTab(newTab);
		}
	}
	
	@Override
	public void setTheme(int resid) {
		super.setTheme(resid);
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
            finish();
            return true;
		case R.id.alert_dialog:
			showAlertDialog();
            return true;
        case R.id.dialog_fragment:
//            new SimpleDialogFragment().show(getFragmentManager(), "FragmentDialog");
            return true;
        case R.id.date_picker_dialog:
            showDatePickerDialog();
            return true;
        case R.id.time_picker_dialog:
            showTimePickerDialog();
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_alert_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_button_positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // positive action
                            }
                        })
                .setNegativeButton(R.string.dialog_button_negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // negative action
                            }
                        }
                );

        builder.show();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { }
        };
        new DatePickerDialog(this, listener, year, month, day).show();
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) { }
        };
        new TimePickerDialog(this, listener, hour, minute,
                DateFormat.is24HourFormat(this)).show();
    }
	
	private final class FragmentTabHolder {
		public final Fragment fragment;
		public final String title;
		public FragmentTabHolder(Fragment fragment, int titleResId) {
			this.fragment = fragment;
			this.title = getString(titleResId);
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		private final List<FragmentTabHolder> mFragments;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<FragmentTabHolder>();
			mFragments.add(new FragmentTabHolder(new ChoicesFragment(), R.string.tab_choices));
			mFragments.add(new FragmentTabHolder(new ButtonFragment(), R.string.tab_buttons));
			mFragments.add(new FragmentTabHolder(new ListFragment(), R.string.tab_list));
			mFragments.add(new FragmentTabHolder(new TextviewFragment(), R.string.tab_text_fields));
            mFragments.add(new FragmentTabHolder(new ProgressFragment(), R.string.tab_progress));
            mFragments.add(new FragmentTabHolder(new PickersFragment(), R.string.tab_pickers));
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position).fragment;
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragments.get(position).title;
		}
	}

}
