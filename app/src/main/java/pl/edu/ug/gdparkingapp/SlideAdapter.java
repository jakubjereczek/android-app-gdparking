package pl.edu.ug.gdparkingapp;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import pl.edu.ug.gdparkingapp.models.ParkingValues;

// ViewPager Adapter
public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    Parking parking;

    public SlideAdapter(Context context, Parking parking) {
        this.context  = context;
        this.parking = parking;
    }

    public int[] backgroundsColors = {
            Color.rgb(3, 218, 197),
            Color.rgb(255, 255, 255)
    };

    private int getRandomColor(int position) {
        if (position % 2 == 0) {
            return backgroundsColors[0];
        }
        return backgroundsColors[1];
    }

    @Override
    public int getCount() {
        return parking.getParkingsList().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider, container, false);
        LinearLayout linearSlide = view.findViewById(R.id.sliderLinearLayout);
        linearSlide.setBackgroundColor(getRandomColor(position));
        ImageView imageSlider = (ImageView) view.findViewById(R.id.sliderImage);
        TextView textTitleSlider  = (TextView) view.findViewById(R.id.sliderTextTitle);
        TextView descriptionTitleSlider  = (TextView) view.findViewById(R.id.sliderTextDescription);

        ParkingValues parkingInfo = parking.getParkingsList().get(position);
        String pattern = "dd.MM.yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String date = simpleDateFormat.format(parkingInfo.getLastUpdate());

        imageSlider.setImageResource(R.drawable.car_icon);
        textTitleSlider.setText(parkingInfo.getParkingName().getName());
        String description = "Dostępnych miejsc parkingowych: <b>" + parkingInfo.getAvailableSpots() + "</b>. <br/> Parking znajduję się na <b>" + parkingInfo.getParkingName().getAddress() + "</b>" + " (wjazd od "+ parkingInfo.getParkingName().getStreetEntrance().trim() + "). <br/> Ostatnia aktualizacja danych nastąpiła <b>" + date + "</b>.";
        descriptionTitleSlider.setText(Html.fromHtml(description));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }



}
