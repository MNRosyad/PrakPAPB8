package com.papb2.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

public class EditTextClear extends AppCompatEditText {
    Drawable clearBtn;
    boolean rtlActive;

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        rtlActive = getResources().getBoolean(R.bool.rtl_active);
        clearBtn = ResourcesCompat.getDrawable
                (getResources(), R.drawable.ic_close_opaque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        setOnTouchListener((v, event) -> {
            if (getCompoundDrawablesRelative()[2] != null) {
                float ltr_clearBtnStart = (getWidth() - getPaddingEnd() - clearBtn.getIntrinsicWidth());
                float rtl_clearBtnStart = (getPaddingEnd() + clearBtn.getIntrinsicWidth());
                boolean btnClicked = false;

                if (rtlActive) {
                    if (event.getX() < rtl_clearBtnStart) {
                        btnClicked = true;
                    }
                } else {
                    if (event.getX() > ltr_clearBtnStart) {
                        btnClicked = true;
                    }
                }

                if (btnClicked) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        clearBtn = ResourcesCompat.getDrawable
                                (getResources(), R.drawable.ic_close_white_24dp, null);
                        showClearBtn();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        clearBtn = ResourcesCompat.getDrawable
                                (getResources(), R.drawable.ic_close_opaque_24dp, null);
                        showClearBtn();
                        Objects.requireNonNull(getText()).clear();
                        hideClearBtn();
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        });
    }

    public EditTextClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearBtn() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearBtn, null);
    }

    private void hideClearBtn() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
