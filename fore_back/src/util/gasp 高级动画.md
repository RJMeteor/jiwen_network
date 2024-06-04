> ~~~
> npm install gsap -S
> 
> 这里的gsap包含了所有的插件和其他工具
> ~~~
>
> [GreenSock | Docs | Plugins | Flip --- 绿袜 |文档 |插件 |空翻](https://greensock.com/docs/v3/GSAP/gsap.utils)
>
> ### gsap的基本使用
>
> ​	首先我们要知道这个库能做什么，`The GreenSock Animation Platform (GSAP)`是一个功能十分强大的动画平台，可以帮助我们实现大部分的动画需求，构建高性能的、适用于所有主要浏览器的高性能动画；GSAP非常的灵活，可以在任何框架上处理页面能够所有通过js改变的元素，不仅可以对div的css属性进行动画，还是SVG、React、Vue、WebGL，甚至和Threejs一起使用。实现补间动画




### Tween

配置：

~~~
callbackScope :用于所有回调（onStart、onUpdate、onComplete等）的范围。
data：将任意数据分配给此属性，它将被附加到补间实例本身，以便您以后可以像 . yourTween.data
delay：动画开始之前的延迟量
duration：动画的持续时间（以秒为单位）。默认值： 0.5
ease：控制动画期间的变化率
id：允许您（可选）为补间实例分配唯一标识符，以便您以后可以找到它 gsap.getById()
paused：如果 ，动画 true 将在创建后立即暂停。默认值： false 。
repeat：动画应重复多少次。所以 repeat: 1 总共会玩两次迭代。默认值： 0 。
repeatDelay：重复之间等待的时间量（以秒为单位）。默认值： 0 。
reversed：true,反方向动画
stagger：如果定义了多个目标，则可以通过设置类似（每个开始时间之间的 0.1 秒）的值 stagger: 0.1 轻松错开每个目标的开始时间。
yoyo：true,以便补间看起来来回移动。
keyframes：要将目标动画设置为各种状态，请使用 keyframes - 充当补间的 vars 对象 to() 数组。例如， keyframes: [{x:100, duration:1}, {y:100, duration:0.5}] 


onStart()：动画开始时要调用的函数（当动画的时间从 0 更改为某个其他值时，如果补间多次重新启动，则可能会多次发生）。
onStartParams：[]，用于传递 onStart 函数的参数数组。
onUpdate()：动画运行中的回调函数
onUpdateParams：[]，用于传递 onUpdate 函数的参数数组。
onInterrupt()：动画中断时要调用的函数 最小动画。
onInterruptParams：[]，用于传递 onInterrupt 函数的参数数组。
onComplete()：动画完成时要调用的函数。
onCompleteParams:[],用于传递 onComplete 函数的参数数组。
onReverseComplete()：当动画从相反方向（不包括重复）再次到达其起点时要调用的函数。
onReverseCompleteParams：[]，用于传递 onReverseComplete 函数的参数数组。
onRepeat()：每次动画进入新的迭代周期（重复）时调用的函数。
onRepeatParams：[]，用于传递 onRepeat 函数的参数数组。
~~~

方法：

~~~
kill()：根据参数完全或部分终止动画。kill 意味着立即停止动画，将其从其父时间轴中删除，然后释放它以进行垃圾回收。
targets()：返回进行动画处理的目标对象的数组。
play()：开始向前播放
pause()：暂停实例
resume()：恢复播放。
restart()：重新向前播放
paused()：获取或设置动画的暂停状态，该状态指示动画当前是否暂停。
seek()：跳转到特定时间，而不影响实例是暂停还是反向。
startTime()：获取或设置动画在其父时间轴上开始的时间（在定义的任何延迟之后）。
endTime()：根据父时间轴的本地时间返回动画完成的时间。
progress()：获取或设置补间的进度，该进度是一个介于 0 和 1 之间的值，指示虚拟播放头（不包括重复）的位置，其中 0 表示开头，0.5 表示完成一半，1 表示完成。
invalidate()：[覆盖]刷新任何内部录制的起始/结束值，如果要重新启动动画而不恢复到任何以前记录的起始值，这将非常有用。
~~~





### Timeline

继承Tween

~~~
add(child:[Tween | Timeline | Label | Callback | Array], position:[Number | String | Label])：将补间、时间线、回调或标签（或其数组）添加到时间线。
call( callback:Function, params:Array, position:)：将回调添加到时间线的末尾（或使用 position 参数在其他地方，默认值 = "+=0"） 
addLabel( label:String, position:[Number | String] )：向时间线添加标签，以便轻松标记重要位置/时间。
nextLabel( time:Number )：从提供的时间返回时间轴中的下一个标签。 time 如果未提供，则将使用时间轴的当前播放头时间。
.removeLabel( label:String )：从时间线中删除标签并返回该标签的时间。
~~~





###### `gsap.effects`

~~~
//向gsap.effects注册
gsap.registerEffect(
	//注册的ID
	name:"renjia",
	//默认动画配置，vars
	defaults:{scale:0.3},
	//是否在timeline中使用
	extendTimeline:true,
	//target目标element对象，config是default配置和传入的配置合并
	effect:(target,config){
		//实现动画
		return gsap.to(target,{...config})
	}	
)

//使用
gsap.effects.renjia(".box");

let tl = gsap.timeline();
tl.renjia(".box", {duration: 3})
  .renjia(".box2", {duration: 1}, "+=2")
  .to(".box3", {x:100});

~~~



###### `gsap.matchMedia()`

匹配媒体

~~~
const media = gsap.matchMedia()
//添加一个
media.add(“(max-width:800)”,(context)=>{
	//是否匹配
	let flag =  context.conditions
	//动画。。。

	return ()=>{
	
	}
})

//添加一个
media.add({
	ismedia:“(max-width:800)”
	},(context)=>{
	//是否匹配
	let {ismedia} =  context.conditions
	//动画。。。
	
	return ()=>{
	
	}
})

//使添加的匹配媒体条件生效
media.revert()
~~~



###### `gsap.getProperty()`

##### Returns : * 返回：*

以数字形式返回所请求的属性的值（如果可能），除非指定一个单位，在这种情况下，该单位将添加到数字中，使其成为字符串。如果不存在，则返回 `null` 。

~~~
gsap.getProperty("#id", "x");              // 20 
gsap.getProperty("#id", "x", "px")         // "20px"
gsap.getProperty("#id", "backgroundColor") // "rgb(255, 128, 0)"
~~~



###### `gsap.getTweensOf()`

返回一个数组，其中包含尚未释放以进行垃圾回收的特定目标（或目标组）的所有补间。

~~~
gsap.to(obj1, {x: 100});
gsap.to(obj2, {x: 100});
gsap.to([obj1, obj2], {opacity: 0});

var a1 = gsap.getTweensOf(obj1); //finds 2 tweens
var a2 = gsap.getTweensOf([obj1, obj2]); //finds 3 tweens
~~~



###### `gsap.isTweening(target)`

##### Returns : Boolean 返回 ： 布尔值

报告特定对象是否正在主动进行动画处理。如果补间已暂停、已完成或尚未开始，则不会将其视为活动状态。

~~~
if (!gsap.isTweening("#id")) {
  // do stuff
}
~~~



###### `gsap.killTweensOf()`

终止特定对象的所有补间（或特定补间属性）或对特定函数的延迟调用。

~~~
gsap.killTweensOf(".myClass");
gsap.killTweensOf(myObject, "opacity,x");
~~~





###### `gsap.utils`	

提供对一些非常有用的实用程序函数的访问



###### `gsap.to()`

##### Returns : [Tween](https://greensock.com/docs/v3/GSAP/Tween) 

定义结束状态

当前状态动画到结束状态

~~~
//第一个参数:绑定元素.box
//第二个元素：定义动作规则
const tween = gsap.to(".box", {rotation: 27, x: 100, duration: 1});
~~~



###### `gsap.quickTo()`

##### Returns : Function

每次向函数传入新数字时，它基本上都会重新启动动画，将其重定向到该新值。它返回（重用的）补间实例。

~~~
let xTo = gsap.quickTo("#id", "x", {duration: 0.4, ease: "power3"}),
    yTo = gsap.quickTo("#id", "y", {duration: 0.4, ease: "power3"});

document.querySelector("#container").addEventListener("mousemove", e => {
  xTo(e.pageX);
  yTo(e.pageY);
});

//从500位置开始，到100位置结束
xTo(100,500)
~~~



###### `gsap.from()`

##### Returns : [Tween](https://greensock.com/docs/v3/GSAP/Tween) 

定义开始状态

开始状态到当前状态

~~~
const tween = gsap.from(".class", {opacity: 0, y: 100, duration: 1});
~~~



###### `gsap.fromTo()`

##### Returns : [Tween](https://greensock.com/docs/v3/GSAP/Tween) 

定义开始状态和结束状态

~~~
//第二个参数：开始状态
//第三个参数：结束状态
gsap.fromTo(".box", {opacity: 0}, {opacity: 0.5, duration: 1});

~~~



###### `gsap.set()`

##### Returns : [Tween](https://greensock.com/docs/v3/GSAP/Tween) 

立即相应地设置目标的属性 - 本质上是具有更直观名称的零持续时间 to（） 补间。

```
gsap.set(".class", {x: 100, y: 50, opacity: 0});
```



###### `gsap.timeline()`

##### Returns : [Timeline](https://greensock.com/docs/v3/GSAP/Timeline) 

时间轴是一种功能强大的`排序`工具，可充当补间和其他时间线的容器，从而可以轻松地将它们作为一个整体进行控制并精确管理其时间。

~~~
//第一个参数：配置排序动画的公共配置
var tl = gsap.timeline({repeat: 2, repeatDelay: 1});

tl.to("#id", {x: 100, duration: 1});
tl.to("#id", {y: 50, duration: 1});
tl.to("#id", {opacity: 0, duration: 1});
~~~

~~~
//开始timeline到3秒，执行该动画
tl.to(".class", {x: 100}, 3);

//相对于运行动画时间线的末尾
tl.to(".class", {x: 100}, "+=1");
tl.to(".class", {x: 100}, "-=1");

//相对于前一个动画开始动画开始1秒后执行该动画
tl.to(".class", {x: 100}, "<=1");

//相对于前一个动画完成后延时1秒执行该动画
tl.to(".class", {x: 100}, ">=1");
~~~



> ### ScrollTrigger的基本使用
>
> ScrollTrigger这个插件是要基于GSAP的，相当于ScrollTrigger仅仅是用来控制触发动画，而GSAP才是用来操作元素。



属性：

~~~
trigger：触发器元素
start：滚动触发器的起始滚动位置
end：滚动触发器的结束滚动位置
scrub：与滚动触发器关联的滚动器元素（或窗口）。它是滚动条链接到滚动触发器的东西。默认情况下，它是窗口（视口）。
markers：是否显示要触发的刻度
pin：引脚元素或boolean

例如：start:"100 top"
start：在距离目标元素(tigger)100位置处，
scroll-start：距离窗口的top位置
只有start经过了scroll-start才会开始动画

~~~

方法：

~~~
ScrollTrigger.addEventListener( type:String, callback:Function ):为以下任何事件添加侦听器：“scrollStart”、“scrollEnd”、“refreshInit”、“revert”、“matchMedia”或“refresh”

ScrollTrigger.removeEventListener( type:String, callback:Function )：删除事件侦听器

ScrollTrigger.getAll()：返回所有滚动触发器实例的数组
disable()：禁用 ScrollTrigger 实例，立即取消固定并还原 ScrollTrigger 对 DOM 所做的任何与引脚相关的更改。
enable()：启用滚动触发器实例
next()：返回刷新顺序中的下一个滚动触发器。
ScrollTrigger.create( vars:Object ) : 创建独立的滚动触发器实例，vars包含滚动触发器的所有配置详细信息的对象
~~~



