package com.app.clockmanager.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.clockmanager.databinding.FragmentPickPeriodBinding
import java.lang.Exception
import java.lang.IllegalArgumentException

class PeriodPickDialogFragment : DialogFragment() {
    private var binding: FragmentPickPeriodBinding? = null
    private var period = 5
    var periodApplyClickListener: OnPeriodApplyClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickPeriodBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            applyPeriod.setOnClickListener {
                periodApplyClickListener?.onClick(period)
                dismiss()
            }

            etPeriod.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let { editable ->
                        val inputString = editable.toString()

                        if (inputString.isBlank()) return

                        try {
                            period = inputString.toInt() * 60 * 1000

                            if (period <= 0) {
                                etPeriod.error = "Дурак блять"
                            }
                        } catch (e: Exception) {
                            etPeriod.error = "Дурак блять"
                        }
                    }
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    fun interface OnPeriodApplyClickListener {
        fun onClick(period: Int)
    }
}