package com.example.wlg.ratewer.Builder;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wlg.ratewer.R;

/**
 * Created by RareHue on 02.07.2015.
 */
public class CustomAlertDialogBuilder  extends Builder{

    private TextView title = null;
    private TextView message = null;
    private ImageView icon = null;

    public CustomAlertDialogBuilder( Context context )
    {
        super( context );

        View customTitle = View.inflate( context, R.layout.alert_dialog_title, null );
        title = (TextView) customTitle.findViewById( R.id.alertTitle );
        icon = (ImageView) customTitle.findViewById( R.id.icon );
        setCustomTitle( customTitle );

        View customMessage = View.inflate( context,
                R.layout.alert_dialog_message, null );
        message = (TextView) customMessage.findViewById( R.id.message );
        setView( customMessage );
    }

    @Override
    public CustomAlertDialogBuilder setTitle( int textResId )
    {
        title.setText( textResId );
        return this;
    }

    @Override
    public CustomAlertDialogBuilder setTitle( CharSequence text )
    {
        title.setText( text );
        return this;
    }

    @Override
    public CustomAlertDialogBuilder setMessage( int textResId )
    {
        message.setText( textResId );
        return this;
    }

    @Override
    public CustomAlertDialogBuilder setMessage( CharSequence text )
    {
        message.setText( text );
        return this;
    }

    @Override
    public CustomAlertDialogBuilder setIcon( int drawableResId )
    {
        icon.setImageResource( drawableResId );
        return this;
    }

    @Override
    public CustomAlertDialogBuilder setIcon( Drawable icon )
    {
        this.icon.setImageDrawable( icon );
        return this;
    }


}