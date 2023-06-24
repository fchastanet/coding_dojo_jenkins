---
marp: true
theme: gaia
size: 16:9
auto-scaling: true
markdown.marp.enableHtml: true
paginate: true
_paginate: false
style: |
  img[alt~="center"] {
    display: block;
    margin: 0 auto;
  }
  img[alt~="right"] {
    float: right;
  }
  img {
    background: #fff url(/images/PrezJenkinsBasics.jpg) no-repeat center center;
    background-size: cover;
  }
header: Coding dojo Jenkinsfile basics
---

# <!--fit--> Coding Dojo Jenkins

![width:800px center Coding Dojo about jenkinsfile](/images/PrezTitle.png)

---

## <!--fit--> Prerequisites (1/2)
<!-- While you’ll listen to my explanations -->

Ensure you have access to and cloned this github project in your wsl environment:

`git clone git@github.com:fchastanet/coding_dojo_jenkins.git`

<!-- Be careful to *not* clone it in windows folder like `/mnt/c` as it cause issues
when settings rights (chmod). -->

From this project directory, run the following command
`./setup.sh`

<!-- it will create necessary ssh keys, for jenkins controller and agent 
being able to communicate. -->

---

## <!-- fit --> Prerequisites (2/2)

update the `.env` file accordingly.
<!-- to avoid ports conflicts, 
But please mind to change the urls provided 
in this coding dojo accordingly too.
-->

Launch the local containers with the following command:
`docker-compose up -d`

<!-- The first time this command will be launched, the needed images will be downloaded. -->

Once the services are up, browse to <http://localhost:8080>.

Afterwards, Jenkins UI will ask you for login(`admin`) and password(`admin`).

---
![bg left:33%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition1.png)

## <!--fit--> What is a Coding Dojo ?

* Programming challenge
  <!--
  Coding dojos are events where we meet altogether to work on a specific 
  programming challenge.
  The objective of the session is to solve a problem so that every attendant
  feel comfortable enough to replicate the solution afterwards
  -->
* Acquire skills
  <!--
  During these events, we are focusing on skills acquisition, preferably in a 
  continuous process. So we hope this session will be the first of a long 
  series. We want to help spreading technical knowledge, tips and tricks and
  share best practices.
  -->
* Collaborative, non-competitive, fun
  <!--
  Dojos are collaborative sessions, where we work together, in a non competitive environment. 
  We want them to be fun moments to spend with colleagues, to try and learn new things
  -->
* Open to all skill levels
  <!--
  Therefore it’s opened to every skill levels. Everyone is welcomed in the sessions, to
  find a master, understand better something difficult, and then become a master.
  -->
* Katas
  <!--
  Sessions are played around Katas, like martial arts.
  Katas are exercises (from simple to complex), that we can try to complete 
  in order to learn something.
  You didn’t understand yet? No pb, just do it again to improve.
  -->
* Pair-programming and TDD
  <!--
  Katas are usually driven by some of the fundamental agile principles like pair-programming and TDD
  Note however, even if I found that it is possible to do Test Driven Development with Jenkinsfile,
  it has not been implemented due to personal lack of experience.
  -->

---
![bg left:33%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition1.png)

## <!--fit--> Why not using copy paste ?

![right width:400 no copy paste icon](/images/PrezNoCopyPaste.png)

<!-- 
  What I will say could be obvious for some of you, or even have non sense at all,
  but just try even if you do not believe me. 
  So why not using copy/paste ?
-->

Better learning ROI
<!-- 
  Typing code instead of copy-pasting provides a better learning Return On Investment,
  mostly because we’re practicing instead of just reading.
-->

Learn better by making mistakes
<!--
  By typing code, you will probably do some mistakes (syntax error for example),
  and most of the time, by making mistakes we better learn.
-->

Hemispheres be connected
<!--
  By typing code, you are thinking and acting at the same time, making your brain
  more attentive, attention is the key for a better understanding.
-->

Kata principle
<!--
  Finally, it is really the principle behind the Kata, reproduce the movement
  until you master it
-->

---

## <!--fit--> Jenkins Basics

![bg left:33%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition1.png)

![right: 66%](/images/PrezJenkinsBasics.jpg)

<!-- 
  Let’s first see a small introduction, no more than 10 minutes
  to better understand what is jenkins and how it works ?
-->

---

## What is Jenkins ?

A Continuous Integration(CI)/Continuous Deployment(CD) tool
![width:900 center CI/CD](/images/PrezCICD.png)

<!--
Continuous integration is focused on automatically building and testing code,
as compared to continuous delivery, which automates the entire software release process up to production.
A Jenkins pipeline will usually start from a code commit, do the build, test, and
deployment to different environments that could include production one.
-->

---

## How does Jenkins work ? (1/5)

<!-- 
  Let’s see quickly how Jenkins work
  Jenkins is based on a Master/Slave architecture
-->

Architecture: Master/Slave
![width:700 center jenkins master/slave](/images/PrezMasterSlave01.png)

---

## How does Jenkins work ? (2/5)

The Jenkins controller is the **master node**
![width:700 center jenkins master node](/images/PrezMasterSlave02.png)

---

## How does Jenkins work ? (3/5)

The Jenkins controller is able to launch **jobs**
![width:700 center jenkins master/slave jobs](/images/PrezMasterSlave03.png)

---

## How does Jenkins work ? (4/5)

jobs are run on different **nodes** directed by an **agent**.
<!-- the term node refers to a machine -->

![width:700 center jenkins master/slave agent](/images/PrezMasterSlave04.png)

---

## How does Jenkins work ? (5/5)

The Agent can then use one or several executors to execute the job(s) depending on configuration.

![width:650 center jenkins master/slave executor](/images/PrezMasterSlave05.png)

<!-- 
  It’s important to note that the master node is the coordinator that 
  is able to launch jobs on nodes.
-->

---

## What is a job ?

Several kinds of job, only pipeline job will be used during this coding dojo.

<!--
Jobs are the heart of Jenkins's build process. 
A job can be considered as a particular task to achieve 
a required objective in Jenkins.
There are several kinds of job that can be run in Jenkins.

You have the ability to create freestyle project that allows you to create a pipeline 
using the UI but it is not recommended.

We will see instead, during this coding dojo, how to create a 
Jenkins pipeline also known as pipeline as code.

We can specifically mention here the Github Organization job that is
responsible to scan the User’s GitHub account for all repositories 
matching some defined markers.
-->

![width:650 center Jenkins job selection](/images/PrezJobs.png)

---

## Coding dojo docker configuration

![width:1100 center Coding dojo docker configuration](/images/PrezCodingDojoDockerConfiguration.png)

<!--
  In this coding dojo, we simulate those machines by docker containers

  **Jenkins service** represents the Jenkins controller that 
  is able to provide the UI (web interface from which you will
  configure your jobs)
  **docker agent** allows building docker images and launching
  docker containers using a specific docker jenkins plugin

  **Jenkins agent** from which most of the coding dojo builds will
  rely on that is also able to run docker in another way and lint
  Dockerfiles.

  Finally 2 other services allowing to simulate sendmail server with a UI and a local docker registry
-->
---

### docker-compose services

For information, the following services are created:

* jenkins: the jenkins server/controller
  <!-- itself including UI web server that will redirect directives to the agent -->
* jenkins-agent: the jenkins agent/node
  <!-- that will run the jenkinsfile directives -->
* docker: docker in docker container used to
  run [docker inside docker](https://hub.docker.com/_/docker) itself
  <!-- this container will be used as a node by the jenkins controller -->
* docker-registry: a local docker registry
  <!-- that will be used in the Exercise 03 -->
* mail-catcher: local smtp and mail web UI
* jenkins-data: service that actually acts as a volume
  <!-- workaround used for docker in docker to work almost seamless -->

---

![bg left:33%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition1.png)

<!--
This is the end of this small technical introduction, now keep prepared to get your hands dirty.

This Coding dojo session will send you on a three exercises journey.
-->
Exercise 1 : your first pipeline based on a simple Jenkinsfile
<!--
First exercise: discover how to create a simple pipeline.
-->

Exercise 2 : a second pipeline with some advanced usage
<!--
The second exercise will teach you some more advanced usage and an introduction to docker plugin.
-->

Exercise 3 : a third pipeline that will build/test/deploy a js project
<!--
The third exercise will show you how to build a complete jenkins pipeline able to build, 
test and deploy a javascript project.
-->

<!--
Finally, if it is too easy for you and you are curious, just check how the docker-compose stack has 
been designed to run this project. There is some additional resources that you can check in the README.md.
-->

![width:400px right:66%](/images/PrezJenkinsExercisesSummary.jpg)

---

![bg left:33%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition2.png)

## <!--fit--> How does it works ?

* You will be broken into pairs
* You will have 40 minutes on the room
<!--
During this time, your facilitators and I will navigate through the rooms to check if you need help
during the exercises.
-->
* After the 40 minutes session we call you back to this room and we discuss

---

![bg left:50%](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransition3.png)

## <!--fit--> Let’s go

You will be switched in your breakout rooms for the first exercise

---

## <!--fit--> Groovy Single quoted string (1/2)

<!--
Jenkinsfile is using groovy language which is a script language derived from Java.
Let’s see some ways to create strings in groovy language
-->
Related documentation: <https://groovy-lang.org/syntax.html#all-strings>

<!-- Example of a single quoted string -->
```groovy
'a single quote string'
```

<!-- Example of a single quoted string with a single quote inside that needs to be escaped -->
```groovy
'an escaped single quote: \' needs a backslash'
```

```groovy
'an escaped escaped character: \\ needs a double backslash'
```

---

## Groovy Strings (1/3)

### Single quoted string

No need to escape single and double quotes
<!--
Triple single quote allows to write multi lines string, you can write single quote 
inside this string without the need to escape it
-->

```groovy
def aMultiLineString = '''
line two with 'single quotes'
line three  with "double quotes"
'''
```

String concatenation
<!-- Use the + operator to concatenate strings -->
```groovy
assert 'ab' = 'a' + 'b'
```

---

## Groovy Strings (2/3)

### String and interpolation

<!--
String interpolation (or variable interpolation,) is the process of evaluating 
a string literal containing one or more placeholders,

Here the name variable will be replaced by Guillaume string during interpolation
-->

```groovy
def name = 'Guillaume' // a plain string
def greeting = "Hello ${name}"
```

<!--
Triple double quotes allows to write multi lines string, you can write single quote or double quotes
inside this string without the need to escape it
-->
```groovy
def name = 'Groovy'
def template = """
  Dear mr ${name}

  You're the winner of the lottery !

  Yours sincerely !
  Dave
"""
```

---

## Groovy Strings (3/3)

### String interpolation tricky example

```groovy
String dockerRegistryUrl='docker-registry:5000'
pipeline {
  stages {
    stage('Docker') {
      steps {
        sh  label: 'build docker image',
            script: """#!/bin/bash
            myBashVariable="hello"
            echo "we need to escape the dollar sign \${myBashVariable}"

            docker build -t "${dockerRegistryUrl}/react-dojo:v1" .
          """
```
<!--
Here a little tricky example that we are often using in Jenkinsfiles

In this example myBashVariable is a local variable inside the shell script
If we want to display this variable, we must escape the dollar sign, otherwise groovy would try to 
interpolate a groovy variable called myBashVariable which would result to a runtime error
On the other hand, the variable dockerRegistryUrl value will be replaced during string interpolation
-->

---

<!-- 
paginate: false
header: "" 
-->

![bg](https://raw.githubusercontent.com/fchastanet/coding_dojo_jenkins/master/images/PrezTransitionDojoRetro.png)

<!--
I hope you enjoyed this coding dojo

How do you feel ?
What did you achieved ?
What is difficult ?
Were the exercises well explained ?

Before closing this coding dojo
Please provide your feedback using the following form {Feedback form link}

And I would like to thank warmly all the craftsmanship community and all the persons involved 
in the preparation of this coding dojo
and particularly {helpers} for the thoroughly tests and code reviews.
{helpers} for the communication, registrations and facilitation.
-->
