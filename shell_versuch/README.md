# Shell with pipes based on Java JNI bindings for native fork(), dup2(), execv() and waitpid()

Java example shell with pipes between forked processes based on Java JNI bindings for native fork(), dup2(), execv(), close(), exit() and waitpid().

This Java source code is pretty similiar to a C implemetation of a shell caused trough Java JNI bindings for C functions and functional Java code style.

[Shell with pipes based on Java JNI bindings for fork(), dup2(), execv() and waitpid()](https://github.com/loadenmb/javaWithStudents/tree/master/shell_versuch)

**Warning: student example, do not use in productive environments**

[Student project overview](https://github.com/loadenmb/javaWithStudents)

## Features / Supported syntax: | < > &&
- | pipe to process
- < from file
- \> to file
- && execute next if process status 0
- determinate binary from include paths

Works / tests:
```shell
/bin/ls                         # output dir content on terminal
ls>/home/loadenmb/abc.txt       # dir content to file
ls|grep abc|grep ab             # shows abc.txt
cat abc.txt                     # file content on terminal
cat<abc.txt                     # file content on terminal
cat-<abc.txt                    # file content on terminal
cat abc.txt-abc.txt<abc.txt     # 3 times abc output
cat abc.txt>xyz.txt             # like cp abc.txt xyz.txt
cat<abc.txt>xxx.txt             # like cp abc.txt xxx.txt
cat>yyy.txt<abc.txt             # like cp abc.txt yyy.txt
cat->zzz.txt<abc.txt            # like cp abc.txt zzz.txt
```
Doesn't work: 
```shell
ls > /home/loadenmb/test.txt    # caused of whitespace before or after command >
ls>mein\ dateiname              # no escape of whitespaces interpreted
ls>"mein dateiname"             # "" not interpreted
```

## Setup
```shell
# ...go to shell_versuch dir, sub dir...
cd cTools/

# compile KernelWrapper (fix java paths in Makefile on inlcude error)
make

# add KernelWrapper to include paths
source source_mich

# compile & run
./javac shell_versuch.java && java shell_versuch
```

## Related
- [Java native interface example (JNI)](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html)
- [reference: execv()](http://man7.org/linux/man-pages/man3/exec.3.html)
- [reference: fork()](http://man7.org/linux/man-pages/man2/fork.2.html)
- [reference: dup2()](http://man7.org/linux/man-pages/man2/dup.2.html)


