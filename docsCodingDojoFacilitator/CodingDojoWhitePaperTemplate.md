# Coding Dojo Jenkinsfile

## What is a Coding Dojo?

This is an event where we gather people to work and learn about a specific topic or challenge.
The main goal is to have fun, meet people and learn!
What is the topic of this one?
At this one we’re going to learn and practice **Basic Jenkinsfile**.

A little citation
> Successful people prove to themselves that they are good at solving problems and that's what
> motivates them.
>
> _Thomas Kunh_

In french:
> Celui qui réussit se prouve à lui-même qu’il est apte à solutionner des problèmes et c’est ce
> qui le motive
>
> _Thomas Kunh_

## How does it work?

We use the **Kata** approach, which means the presenter will pose a specific challenge to the participants
and will show them how to overcome it.
We’re going to group everybody in pairs, considering the overall experience in Software Development
of each member to build the pairs.

## Who can participate?

Anyone can attend it, it’s a place to learn even if you have never tried Jenkinsfile before.
In the registration form we ask about Software Development experience and Jenkins level only to build
balanced pairs, so don’t worry if you don’t know Jenkins/Jenkinsfile at all. However, we recommend you
to at least know programming to have a better experience.

## What do I need to do to participate?

Register through the Registration Form in the following link {RegistrationFormLink}

## What is the schedule?

We’re planning it for {available dates} depending on {countries} time zones, and we’re going
to schedule sessions and send the invites based on registrations.

Usually, the sessions occur from 9am to 12:30pm, but it can be adjusted according to the time zones.

## Prerequisites

Please be sure to fulfill these requirements before attending a dojo session.

### Docker

The Dojo session requires you to run Docker containers.
It is strongly advised to install docker from wsl 2. You can use the following documentation
[Wsl development environment installation](/WslDevenvInstallation.md)

Do not forget to install docker-compose too !

Do not hesitate to contact {point of contact} if you encounter difficulties to install
or configure your environment.

### Docker Images

All the exercises are played using a local Jenkins instance configured using docker-compose.
So be sure to pull the images prior to join the session, it’ll save us a lot of time.

To do this, from your terminal type the following command:

```bash
docker pull dockage/mailcatcher:0.8.2
docker pull alpine:3.18.0
docker pull scrasnups/jenkins-dojo-blueocean:v2
docker pull scrasnups/jenkins-dojo-ssh-agent:v2
docker pull docker:24-dind
docker pull registry:2.8.2
```

### Configured IDE

Please come with your favorite IDE well-configured.
We encourage you to make use of a collaborative coding plugin to ease pair programming.

#### Visual Studio Users

Visual Studio users can use Live Share plugin:
<https://marketplace.visualstudio.com/items?itemName=MS-vsliveshare.vsls-vs>

#### VS Code Users

VS Code users can use CodeTogether plugin:
<https://marketplace.visualstudio.com/items?itemName=genuitecllc.codetogether>

#### Without collaborative plugin

You can still participate by sharing screen or using git pull/push strategy.
Audio, microphone, camera, and good mood :)
The coding dojo approach will emphasize and encourage collaborative practices in small coding in pairs.
Therefore, every possible option to make the moment as lively and enjoyable as possible is welcomed!
