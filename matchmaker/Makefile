buildGCR:
	gcloud builds submit --tag gcr.io/openstep-website/matchmaker
deployCloudRun:
	gcloud run deploy --image gcr.io/openstep-website/matchmaker  --platform managed --service-account openstep@openstep-website.iam.gserviceaccount.com --set-env-vars GOOGLE_APPLICATION_CREDENTIALS=/app/creds.json --memory 1024
