package com.uyou.copenaccount.reader.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.uyou.copenaccount.R;


public class ReaderMyDialog extends Dialog {

    private Builder builder = null;

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    protected ReaderMyDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ReaderMyDialog(Context context, int theme) {
        super(context, theme);
    }

    public Button getButton(int i) {
        if (builder == null)
            return null;
        if (AlertDialog.BUTTON_NEGATIVE == i) {
            return builder.getNegativeButton();
        } else if (AlertDialog.BUTTON_POSITIVE == i) {
            return builder.getPositiveButton();
        } else if (AlertDialog.BUTTON_NEUTRAL == i) {
            return null;
        } else {
            return null;
        }
    }

    public ReaderMyDialog(Context context) {
        super(context);
    }

    public void setMessage(String message) {
        if (builder != null) {
            builder.getTv_message().setText(message);
        }
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private TextView tv_title, tv_message;
        private Button button_two_leftbtn, button_two_rightbtn;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public TextView getTv_title() {
            return tv_title;
        }

        public TextView getTv_message() {
            return tv_message;
        }

        public Button getNegativeButton() {
            return button_two_leftbtn;
        }

        public Button getPositiveButton() {
            return button_two_rightbtn;
        }

        /**
         * Set the Dialog reader_dialog_title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog reader_dialog_title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public ReaderMyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final ReaderMyDialog dialog = new ReaderMyDialog(context, R.style.com_reader_my_dialog);
            dialog.setBuilder(this);
            View layout = inflater.inflate(R.layout.reader_my_dialog, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog reader_dialog_title
            tv_title = (TextView) layout.findViewById(R.id.title_tv);
            tv_title.setText(title);
            // set the confirm button
            button_two_rightbtn = (Button) layout
                    .findViewById(R.id.button_two_rightbtn);
            if (positiveButtonText != null) {
                // tv_button = (Button)
                // layout.findViewById(R.id.button_one_btn);
                button_two_rightbtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    button_two_rightbtn
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                } else {
                    button_two_rightbtn
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                }
            } else {
                // if no confirm button just set the visibility to GONE
                button_two_rightbtn.setVisibility(View.GONE);
            }

            button_two_leftbtn = (Button) layout
                    .findViewById(R.id.button_two_leftbtn);
            if (negativeButtonText != null) {
                button_two_leftbtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {

                    button_two_leftbtn
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                } else {
                    button_two_leftbtn
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss(); // 默认给按钮赋值点击事件。隐藏按钮
                                }
                            });

                }
            } else {
                button_two_leftbtn.setVisibility(View.GONE);
            }

            tv_message = ((TextView) layout.findViewById(R.id.message_tv));
            if (message != null) {
                tv_message.setText(message);
            }
            // else if (contentView != null) {
            // // if no message set
            // // add the contentView to the dialog body
            // ((LinearLayout) layout.findViewById(R.id.message_tv))
            // .removeAllViews();
            // ((LinearLayout) layout.findViewById(R.id.message_tv)).addView(
            // contentView, new LayoutParams(
            // LayoutParams.WRAP_CONTENT,
            // LayoutParams.WRAP_CONTENT));
            // }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
