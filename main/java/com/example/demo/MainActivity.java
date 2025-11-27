package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.demo.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Integer> data = new ArrayList<>();
    List<Integer> data1 = new ArrayList<>();

    ActivityMainBinding binding;

    private WebFragment currentFragment;
    //删除
    private ActivityResultLauncher<Intent> deleteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result->{
                if(result.getResultCode()==RESULT_OK) {
                    Intent dataGet = result.getData();
                    int deletePosition = dataGet.getIntExtra("ID", -1);
                    boolean isDeleted = deletePosition == data.size() - 1;
                    if (deletePosition!=-1&&deletePosition<data.size()) {
                        data.remove(deletePosition);
                        data1.remove(deletePosition);
                        RecycleAdapter titleAdapter = (RecycleAdapter) binding.title.getAdapter();
                        titleAdapter.notifyItemRemoved(deletePosition);
                        titleAdapter.notifyItemRangeChanged(deletePosition, data.size());
                        ViewPager2Adapter viewPager2Adapter = (ViewPager2Adapter) binding.content.getAdapter();
                        viewPager2Adapter.notifyItemRemoved(deletePosition);
                        viewPager2Adapter.notifyItemRangeChanged(deletePosition, data1.size());
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //头像的设置
        data.add(R.drawable.ic_launcher_background);
        for(int i=0;i<6;i++){
        data.add(R.mipmap.ic_launcher_round);}
        data.add(R.drawable.ic_launcher_background);

        setContentView(binding.getRoot());
        binding.title.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.title.setAdapter(new RecycleAdapter(data));

        //详细信息的设置
        data1.add(R.drawable.ic_launcher_background);
        for(int i=0;i<6;i++){
            data1.add(R.mipmap.ic_launcher_round);}
        data1.add(R.drawable.ic_launcher_background);

        binding.content.setAdapter(new ViewPager2Adapter(data1));

        //监听viewPager
        binding.content.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.title.smoothScrollToPosition(position);
            }
        });
        //监听recycleView
        RecycleAdapter titleAdapter=(RecycleAdapter)binding.title.getAdapter();
        titleAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                binding.content.setCurrentItem(position,true);
                //避免fragment错误显示
                if(currentFragment!=null){
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment).commit();
                    currentFragment=null;
                }
            }
        });


        titleAdapter.setOnItemLongClickListener(position -> {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, DetailActivity.class);
            intent.putExtra("IMAGE",data.get(position));
            intent.putExtra("ID",position);
            deleteLauncher.launch(intent);
        });


        binding.btn1.setOnClickListener(v->{
            int currentPage =binding.content.getCurrentItem();
            if(currentFragment!=null){
                if(currentFragment.isVisible()){
                    // 隐藏网页，显示图片
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment).commitAllowingStateLoss();
                    binding.content.setVisibility(View.VISIBLE);
                } else {
                    // 隐藏图片，显示网页
                    binding.content.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .show(currentFragment).commitAllowingStateLoss();
                }
            } else {
                // 新建网页，挂载正确容器
                currentFragment = WebFragment.newInstance(currentPage);
                binding.content.setVisibility(View.GONE); // 隐藏图片
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, currentFragment)
                        .show(currentFragment)
                        .commitAllowingStateLoss();
            }
        });
//            if(currentFragment.isVisible()){
//                getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
//            }
//            else {
//                currentFragment = WebFragment.newInstance(currentPage);
//                getSupportFragmentManager().beginTransaction().add(R.id.content,currentFragment)
//                        .show(currentFragment).commit();
//            }
//        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    //用来测试接入网络界面的
//    public void onClick(View view){
//        Intent intent=new Intent();
//        intent.setClass(this,LocalWebActivity.class);
//        startActivity(intent);
//    }
}