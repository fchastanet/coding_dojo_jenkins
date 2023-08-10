# Coding Dojo Jenkins

![Coding Dojo about jenkinsfile](/images/PrezTitle.png)

The aim of this coding dojo is to practice Jenkinsfile format by following 3 exercises.
At the end of this coding dojo, the final Jenkinsfile will allow to:

- build docker image with nodejs inside
- lint the project
- unit test the project
- create a build package, we will simply create a tar.gz archive
  - add a parameter to build the package (optional stage build)

## Pre-requisites

Send this [white paper template](/docsCodingDojoFacilitator/CodingDojoWhitePaperTemplate.md) to your
attendees.

## Coding dojo day

The coding dojo facilitator will read this [Presentation slides](/docs/CodingDojoSlides.md)

## Installation

Clone this project in wsl:
`git clone git@github.com:fchastanet/coding_dojo_jenkins.git`

**Important**: Be careful to *not* clone it in windows folder like `/mnt/c` as it cause issues
when settings rights (chmod) or performance issues.

From this project directory, run the following command that will create necessary
ssh keys, for jenkins controller and agent being able to communicate.
`./setup.sh`

Setup the local containers with the following command:
`docker-compose up -d`

The first time this command will be launched, the needed images will be downloaded and customized
jenkins server image will be built.

Once the services are up, browse to <http://localhost:8080>.

Afterwards, Jenkins UI will ask you for login(`admin`) and password(`admin`).

If you have ports conflicts, please update the `.env` file accordingly. But please mind to change the
urls provided in this coding dojo accordingly too.

Let's start by the [Exercise 1](/Exercise01 - Your first inline pipeline.md). Happy coding dojo !

## Cleaning at the end of the Dojo

Please ensure that at least you stopped all the containers to avoid consuming resources for nothing

```bash
(cd react-dojo && docker-compose down --rmi all -v)
docker-compose down --rmi all -v
```

## Resources

[coding dojo slides](/docs/CodingDojoSlides.md)

[Jenkins Glossary](https://www.jenkins.io/doc/book/glossary/): explains the terms Agent,
Controller, Step, Stage, ...

[How to write a Jenkinsfile](/docs/HowToWrite-Jenkinsfile.md)

## Past sessions

### Session 1 - 2023-06-21

#### Session 1 - Encountered Issues and fixes

- used the c drive mounted directory in wsl - causing errors when settings the rights of
  the ssh keys
  - added a note about that in Installation section
- no space left on device, causing build to never end
  - I helped the participant to clean up some space on his laptop
- some people forgot to run setup.sh
  - making agent not able to connect to the controller
- Exercise 03 step 01, one participant "automatically" fixes the Jenkinsfile typo
  in the example with an error, so he was not understanding the reason of the exercise.
- One participant thought setup.sh would have updated its own ssh key, so skipped the use of it
  - I added some additional information about this script in Installation section

#### Session 1 - feedbacks

There was 10 participants during this session.
Most of the participants liked the coding dojo content.

From the 4 responses received from the survey:

- Nobody found the content too difficult, one participant found it too easy
- all the participants that answered the survey reached exercise 3
- 3 participants reached exercise 3 step 5, one completed everything
- How useful was the coding dojo for your day to day work? 4/5
- Would you participate to another coding dojo in the future? 100% yes
- In your opinion, on which topic should be the next coding dojo ?
  - about jenkins library
- Please add any additional comments
  - Congratulations for organizers ! What an excellent work with lots of details !
  - I appreciated that in the Readme of the project, we can find a lot of information about
    Jenkinsfile, how it works, give tips, explain the good practices. So a lot of useful info!
  - Explanations/steps perfectly detailed with the right amount of additional reading/documentation,
    nothing to "guess" (in a previous coding dojo, it was impossible to advance without some knowledge
    of the technology because the organizers didn't give "details for level zero people")
    This was perfectly planned and organized.
  - The content of this coding dojo was just what I was expecting

### Session 2 - 2023-06-23

#### Session 2 - Encountered Issues and fixes

- some participants was using ubuntu 22.04, setup.sh was using ssh-keygen of the wsl host
  - when ssh-key was used by jenkins controller, the ssh key was not recognized (invalid format)
  - I fixed the issue during the session, by providing a fix in setup.sh generating ssh key using
    the container itself
- issue with ssh commands not working because of ssh command version not the right one in the container
- one participant has dns issue during jenkins build of Exo03 making npm ci failing
  - relaunching the build has fixed the issue
- some participants was having issues with their docker installation (missing docker-compose,
  docker command as root)
  - I provided this script, but it is not working anymore with Ubuntu22.04

```bash
bash <(curl -s https://raw.githubusercontent.com/fchastanet/bash-dev-env/master/snippets/installDockerInWsl.sh)
```

- some participants was using a Macintosh laptop
  - I do not have any solution to this issue, Pull requests are welcomed !
