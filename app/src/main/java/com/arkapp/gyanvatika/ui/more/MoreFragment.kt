package com.arkapp.gyanvatika.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.databinding.FragmentMoreBinding
import com.arkapp.gyanvatika.utils.capitalizeFirstWords
import com.arkapp.gyanvatika.utils.isNotDoubleClicked
import com.arkapp.gyanvatika.utils.showDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_more.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class MoreFragment : Fragment(), KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein by kodein()

    private val session: PrefSession by instance()

    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)

        viewModel = ViewModelProviders.of(this).get(MoreViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.username = "Welcome\n${FirebaseAuth.getInstance().currentUser?.displayName.capitalizeFirstWords()}"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBookingSwitch.isChecked = session.showBookingAmount()

        val dialog = context!!.showDialog(
            getString(R.string.log_out)
            , getString(R.string.logout_desc),
            getString(R.string.cancel))

        dialog.setPositiveButton(getString(R.string.log_out)) { mDialog, _ ->
            signOut()
            mDialog.dismiss()
        }

        signOutTv.setOnClickListener {
            dialog.show()
        }

        showBookingSwitch.setOnCheckedChangeListener { _, isChecked ->
            session.showBookingAmount(isChecked)
        }

        showBookingAmountTv.setOnClickListener {
            if (isNotDoubleClicked(1))
                showBookingSwitch.toggle()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(context!!)
            .addOnCompleteListener {
                activity!!.finishAffinity()
                session.clearData()
            }
    }


}
