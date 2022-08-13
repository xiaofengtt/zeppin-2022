AndroidBucket
=============

###Android开发常用整理（不断扩充中）<br/>包含各种工具类、线程池、日志、自定义的控件、程序崩溃捕捉处理、默认的Application配置、常用的Adapter等

###注意: <br/>
> 如果需要使用FragmentTabAdapter，则需要android-support-v4.jar的支持（以兼容低版本）

###使用方法<br/>
        clone代码，并在项目中引入AndroidBucket。
        
###Gradle([Check newest version](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22AndroidBucket%22))
        compile 'com.github.wangjiegulu:AndroidBucket:x.x.x'
###Maven([Check newest version](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22AndroidBucket%22))
        <dependency>
          <groupId>com.github.wangjiegulu</groupId>
          <artifactId>AndroidBucket</artifactId>
          <version>x.x.x</version>
          <type>aar</type>
        </dependency>

###线程池、日志、程序崩溃捕捉处理等配置方法<br/>
        新建MyApplication，继承ABApplication，根据需要实现里面的方法
        initThreadPool(); // 初始化线程池
        initLogger(); // 初始化日志工具
        initImageLoader(); // 初始化图片加载器
        initCrashHandler(); // 初始化程序崩溃捕捉处理
        注意：除了图片加载器外，其他的初始化操作ABApplication都提供了默认的初始化支持。
        图片加载器推荐使用[ImageLoaderSample](https://github.com/wangjiegulu/ImageLoaderSample)项目。

        MyApplication：
        public class ABApplication extends Application{
            @Override
            public void onCreate() {
                super.onCreate();
            }

            /**
             * 初始化线程池
             */
            protected void initThreadPool(){
                ThreadPool.initThreadPool(7);
            }

            /**
             * 初始化日志
             */
            protected void initLogger(){
                Logger.initLogger(null);
            }

            /**
             * 初始化图片加载器
             */
            protected void initImageLoader(){

            }

            /**
             * 初始化程序崩溃捕捉处理
             */
            protected void initCrashHandler(){
                ABCrashHandler.init(getApplicationContext());
            }

        }

License
=======

    Copyright 2013 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing blacklist and
    limitations under the License.



[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidBucket-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1579)
