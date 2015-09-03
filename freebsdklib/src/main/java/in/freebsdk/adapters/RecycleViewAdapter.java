/*package in.freebsdk.adapters;

import in.freeb.sdk.R;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

public class RecycleViewAdapter extends BaseAdapter {

	Context context;
	private AQuery aQuery;
	private int flag = -1;
	Typeface typeFace;
	private List<OffersData.Offer> mList;
	// FragmentManager fragmentManager;
	public String VIDEO_ID = "";
	private int lastPosition = -1;
	private ListView listView;

	public RecycleViewAdapter(Context context, List<OffersData.Offer> list,
			ListView listView) {

		this.context = context;
		// fragmentManager = fm;
		this.mList = list;
		try {
			typeFace = Typeface.createFromAsset(this.context.getAssets(),
					"fonts/Rupee_Foradian.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		aQuery = new AQuery(context);
		this.listView = listView;
	}

	@Override
	public int getItemViewType(int position) {
		try {
			if (mList != null
					&& mList.get(position).getOfferType().equals("APP")) {

				// Log.e("OFFER TYPE",">>>>>"+mList.get(position).getOfferType());
				return 0;
			}

		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public class ViewHolder {

		private View viewAbove;
		private ImageView vendorImage;
		private TextView vendorName, rupees, appCategory;
		private TextView appDescription;
		private TextView installBtn, earnCashTextView;
		private ImageView bannerImage;
		private RelativeLayout app_offers_relative_layout;
		private RelativeLayout layout_back;
		private LinearLayout nullItemLayout;
		private FrameLayout offerFrameLayout;

		public ViewHolder(View itemView, int flag) {
			if (itemView != null) {

				if (flag == 0) {
					vendorImage = (ImageView) itemView
							.findViewById(R.id.iv_app_icon);
					viewAbove = (View) itemView.findViewById(R.id.upper_view);
					vendorName = (TextView) itemView
							.findViewById(R.id.tv_app_name);
					rupees = (TextView) itemView.findViewById(R.id.tv_amount);
					appCategory = (TextView) itemView
							.findViewById(R.id.tv_app_category);
					appDescription = (TextView) itemView
							.findViewById(R.id.tv_app_description);
					installBtn = (TextView) itemView
							.findViewById(R.id.install_btn);
					earnCashTextView = (TextView) itemView
							.findViewById(R.id.earnCashTextView);
					app_offers_relative_layout = (RelativeLayout) itemView
							.findViewById(R.id.app_offers_relative_layout);
					layout_back = (RelativeLayout) itemView
							.findViewById(R.id.layout_back);
					nullItemLayout = (LinearLayout) itemView
							.findViewById(R.id.nullItemLayout);
					offerFrameLayout = (FrameLayout) itemView
							.findViewById(R.id.app_offer_frameLayout);
				} else {

				}

			}

		}

	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		flag = 0;
		Offer dataObject = null;
		final int viewType = getItemViewType(position);
		String offerType = "", iconUrl = "", offerPrice = "", rgbCode = "", shortDesc = "", offerCategory = "", appName = "", installText = "", earnCashText = "";

		if (mList != null && mList.size() > 0) {
			dataObject = mList.get(position);
			offerType = dataObject.getOfferType();
			iconUrl = dataObject.getIconUrl();
			offerPrice = dataObject.getOfferPrice();
			rgbCode = dataObject.getRgbCode();
			shortDesc = dataObject.getShortDesc();
			offerCategory = dataObject.getOfferCategory();
			appName = dataObject.getAppName();

		}

		ViewHolder viewHolder = null;
		try {

			if (view == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				if (viewType == 0) {

					view = inflater.inflate(R.layout.app_offers, parent, false);
					flag = 0;
					viewHolder = new ViewHolder(view, flag);

					view.setTag(viewHolder);
				}

				else if (viewType == 1) {
				}

			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

		} catch (Exception e1) {
		}

		// if(position%2==0)
		// {
		// viewHolder.app_offers_relative_layout.setBackgroundColor(Color.parseColor("#ff6d00"));
		// }

		try {
			if (viewType == 0) {

				if (dataObject.getAlreadyConsumed().equals("1")) {
					viewHolder.nullItemLayout.setVisibility(View.VISIBLE);
					viewHolder.offerFrameLayout.setVisibility(View.GONE);
					listView.setDividerHeight(0);
				} else {

					viewHolder.nullItemLayout.setVisibility(View.GONE);
					viewHolder.offerFrameLayout.setVisibility(View.VISIBLE);
					float heightpx = TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_PX, 7, context
									.getResources().getDisplayMetrics());
					listView.setDividerHeight((int) heightpx);
				}

				view.setVisibility(View.VISIBLE);

				AQuery imgAquery = aQuery.recycle(view);
				imgAquery.id(viewHolder.vendorImage).image(iconUrl, true, true,
						0, R.drawable.ic_launcher, null, AQuery.FADE_IN);
				viewHolder.vendorName.setText(appName);
				String colorArray[] = dataObject.getRgbCode().split("\\|");
				// viewHolder.vendorName.setTextColor(Color
				// .parseColor(colorArray[0]));

				if (dataObject.getUdf4() != null
						&& dataObject.getUdf4().equalsIgnoreCase("true")) {
					// viewHolder.rupees.setTypeface(typeFace);
					viewHolder.rupees.setText(Html
							.fromHtml(context.getResources().getString(
									R.string.rs)
									+ offerPrice));
					viewHolder.rupees.setTextSize(20);
					// viewHolder.rupees.setMovementMethod(LinkMovementMethod
					// .getInstance());

				} else {
					// viewHolder.rupees.setText(Html.fromHtml(offerPrice));
					// viewHolder.rupees.setMovementMethod(LinkMovementMethod
					// .getInstance());
				}

				viewHolder.appCategory.setText(offerCategory);

				viewHolder.appDescription.setText(shortDesc);
				Log.e("INSTALL TEXT", ">>>>" + dataObject.getUdf2());
				Log.e("INSTALL dataObject.getUdf4",
						">>>>" + dataObject.getUdf4());
				String installEarnText[] = dataObject.getUdf2().split("\\|");
				installText = installEarnText[1];
				earnCashText = installEarnText[0];

				// Log.e("INSTALL TEXT",">>>>"+installText);
				try {

					viewHolder.installBtn.setText(installText);
					viewHolder.earnCashTextView.setText(earnCashText);
					viewHolder.appCategory.setTextColor(Color
							.parseColor(colorArray[1]));
					viewHolder.appDescription.setTextColor(Color
							.parseColor(colorArray[2]));
					viewHolder.earnCashTextView.setTextColor(Color
							.parseColor(colorArray[3]));
					// viewHolder.app_offers_relative_layout
					// .setBackgroundColor(Color.parseColor(colorArray[4]));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		try {
			if (view != null) {
				Animation animation = AnimationUtils.loadAnimation(context,
						(position > lastPosition) ? R.anim.up_from_bottom
								: R.anim.down_from_up);
				view.startAnimation(animation);
				lastPosition = position;
			}
		} catch (NotFoundException e) {
		} catch (Exception e) {
		}

		return view;
	}

}
*/