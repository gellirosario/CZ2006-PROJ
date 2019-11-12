package com.cz2006.helloworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cz2006.helloworld.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

/**
 * Represents Slider Adapter for FAQ
 *
 * @author Rosario Gelli Ann
 *
 */
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        // Example images
        switch (position) {
            case 0:
                viewHolder.textViewDescription.setText("Why Recycle");
                Glide.with(viewHolder.itemView)
                        .load("https://s2.studylib.net/store/data/005574757_2-04e824f9778e798244f06122ebd3dbd8.png")
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                viewHolder.textViewDescription.setText("What to recycle");
                Glide.with(viewHolder.itemView)
                        .load("https://www.veolia.co.uk/sheffield/sites/g/files/dvc1851/files/styles/default/public/image/2019/09/Sheffield%20Bins%20Guide%201.PNG?itok=U0Hsa4wy")
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                viewHolder.textViewDescription.setText("Recycling Fact");
                Glide.with(viewHolder.itemView)
                        .load("https://recyclegarb.com/wp-content/uploads/2018/05/IMG_20180502_145500_126.jpg")
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.textViewDescription.setText("Recycle Your E-Waste Now");
                Glide.with(viewHolder.itemView)
                        .load("https://techcollect.com.au/wp-content/uploads/2017/10/What-is-stopping-us-from-recycling.jpg")
                        .into(viewHolder.imageViewBackground);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}