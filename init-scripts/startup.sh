echo 'creating Queue'

awslocal sqs create-queue \
  --queue-name sample-queue.fifo \
  --attributes FifoQueue=true \
  --region us-east-1