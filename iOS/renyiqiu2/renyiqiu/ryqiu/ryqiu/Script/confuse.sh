#!/usr/bin/env bash

TABLENAME=symbols
SYMBOL_DB_FILE="symbols"
STRING_SYMBOL_FILE="$PROJECT_DIR/VHLObfuscationDemo/VHLObfuscation/func.list"

CONFUSE_FILE="$PROJECT_DIR/VHLObfuscationDemo"
HEAD_FILE="$PROJECT_DIR/VHLObfuscationDemo/VHLObfuscation/codeObfuscation.h"

export LC_CTYPE=C

# ** 可以不执行下面的方法，替换成自己的规则手动写入到 func.list 中**
# 取以.m或.h结尾的文件以+号或-号开头的行，并以 vhl_ 开头的方法 |去掉所有+号或－号|用空格代替符号|n个空格跟着<号 替换成 <号|开头不能是IBAction|用空格split字串取第二部分|排序|去重复|删除空行|删掉以init开头的行>写进func.list
grep -h -r -I  "^[-+]" $CONFUSE_FILE  --include '*.[mh]' |sed "s/[+-]//g"|sed "s/[();,: *\^\/\{]/ /g"|sed "s/[ ]*</</"| sed "/^[ ]*IBAction/d"|awk '{split($0,b," "); print b[2]; }'| sort|uniq |sed "/^$/d"|sed -n "/^vhl_/p" >$STRING_SYMBOL_FILE
