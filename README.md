# ElasticLayout
安卓多item抽屉动画效果,支持动态添加删除动画。
先看效果：


![这里写图片描述](http://img.blog.csdn.net/20161108154359500)

本文CSDN博客
http://blog.csdn.net/AndroidMsky/article/details/53083634


先解释一下标题不然被说成标题党可不好，为什么是超轻量，因为我知道用Listview和RecyclerView可以实现这样的效果，但是大家都知这二者都是需要adapter去适配数据，用起来比较麻烦，而且二者默认都是可以滑动的。

那么，当你遇到如下需求，关键人信息中，关键人数量是动态的，但是不会太多。整个页面是可以上下滑动的，关键人信息，基本资料，财务信息，都是可以展开和关闭的，里面都有5项左右的信息，如图：


![](http://img.blog.csdn.net/20161108155219442)
      



面对这个需求你怎么办？小李说，可以用listview加载不同样式的布局，然后写在一个list里，当用户展开关键人信息的时候去移除或者添加一些item然后重新适配listview，小李啪啪啪300行代码搞出来了，效果还不错，只是展开时候没有动画。小明马上来了说小李listview已经快退出历史舞台了用起来不如RecyclerView，而且RecyclerView对动画的支持也更加友好，好的，我来啪啪啪写出150行代码，小李用RecyclerView实现了需求。
然而他们的项目经理对二人的代码都不满意，首先小李的没有过渡动画代码量太大，小明的呢，可以还不错，不过多样式逻辑复杂，数据不多，RecyclerView的view复用优势也不明显。用一个LinearLayout，如果超长需要滑动用ScrollView好不好？于是小李跑回去实现了效果。

首先准备一个工具类，以属性动画的形式改变View的高度：
 
分别传入view，view当前高度，view结束高度，动画时间毫秒。

```
public static void anim(final View view, int startHeight, int endHeight, int duration){
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "view", startHeight,endHeight ).setDuration(duration);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int cVal = (int) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.height = cVal;
                view.setLayoutParams(lp);
            }
        });
    }
```
dp px的转换（因为xml多用的dp去定义高度）
```
public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
```
展开View代码:

```

anim(mLinearLayout2, 0, l2Height, 500);
```
折叠View代码：
```
 //记录一下当前高度方便下次展开
l2Height = mLinearLayout2.getHeight();
AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), 0, 500);


    }
```
确实很简单有么有，最基本的抽屉效果就出来了。

然后是如何add一行进来:
用一个view的list去管理动态的view item，先进入list然后加入我们的
LinearLayout并且对LinearLayout的高度施展动画大法，ADD view成功：
```
public void add(View v) {

nowNumber++;
final View view = LayoutInflater.from(this).inflate(R.layout.item, null);
TextView textView = (TextView) view.findViewById(R.id.tv_item);
mViews.add(view);
textView.setText("num:" + nowNumber);
mLinearLayout2.addView(view);
AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), mLinearLayout2.getHeight() + AnimTools.dip2px(this, 50), 200);


```
如何del一个view：

先施展动画然后再romove view，可以根据自己的需要更改这里的逻辑

```
 AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), mLinearLayout2.getHeight() - AnimTools.dip2px(this, 50), 200);

        new Handler().postDelayed(new Runnable() {

            public void run() {

          mLinearLayout2.removeView(mViews.get(mViews.size() - 2));
mViews.remove(mViews.size() - 2);}

        }, 200);
```

就这样不到100行，逻辑清晰，抽屉效果就实现了，笔者也鼓励大家用不同的方法去干同一件事情，肯定会有不同的发现，不要见到多item就想起Listview就RecyclerView，这种定向的思维在编程的世界里是可怕的。请不要局限自己的思维。

欢迎关注作者。欢迎评论讨论。欢迎拍砖。 
如果觉得这篇文章对你有帮助 欢迎star我的github。也算对笔者的一种支持。 
本文Github代码链接 
https://github.com/AndroidMsky/ElasticLayout

欢迎加作者自营安卓开发交流群：308372687
![这里写图片描述](http://img.blog.csdn.net/20161028111556438)

博主原创未经允许不许转载。






